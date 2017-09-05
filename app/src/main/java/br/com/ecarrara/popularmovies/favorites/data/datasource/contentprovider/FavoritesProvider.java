package br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import br.com.ecarrara.popularmovies.core.data.datasource.contentprovider.ContentProviderContract;
import br.com.ecarrara.popularmovies.core.data.datasource.sqlite.SqliteDatabaseHelper;
import br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider.FavoritesContract.FavoriteEntry;
import br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider.FavoritesContract.FavoriteMovieEntry;

public class FavoritesProvider extends ContentProvider {

    // Uri matcher ids
    private static final int FAVORITES = 100;
    private static final int SPECIFIC_FAVORITE = 101;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = ContentProviderContract.CONTENT_AUTHORITY;
        uriMatcher.addURI(authority, FavoritesContract.PATH_FAVORITE, FAVORITES);
        uriMatcher.addURI(authority, FavoritesContract.PATH_FAVORITE + "/#", SPECIFIC_FAVORITE);
        return uriMatcher;
    }

    private static final String[] ALL_FAVORITES_PROJECTION = {
            FavoriteEntry.TABLE_NAME + "." + FavoriteEntry.COLUMN_ID,
            FavoriteMovieEntry.TABLE_NAME + "." + FavoriteMovieEntry.COLUMN_ID,
            FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE,
            FavoriteMovieEntry.COLUMN_OVERVIEW,
            FavoriteMovieEntry.COLUMN_RELEASE_DATE,
            FavoriteMovieEntry.COLUMN_POPULARITY,
            FavoriteMovieEntry.COLUMN_VOTE_AVERAGE,
            FavoriteMovieEntry.COLUMN_POSTER_PATH
    };

    private static final SQLiteQueryBuilder allFavoriteInformationQueryBuilder;

    static {
        allFavoriteInformationQueryBuilder = new SQLiteQueryBuilder();
        allFavoriteInformationQueryBuilder.setTables(
                FavoriteEntry.TABLE_NAME + " INNER JOIN " + FavoriteMovieEntry.TABLE_NAME +
                        " ON " + FavoriteEntry.TABLE_NAME + "." + FavoriteEntry.COLUMN_MOVIE_ID +
                        " = " + FavoriteMovieEntry.TABLE_NAME + "." + FavoriteMovieEntry.COLUMN_ID
        );
    }

    private SqliteDatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        databaseHelper = new SqliteDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int matchedUriId = uriMatcher.match(uri);
        switch (matchedUriId) {
            case FAVORITES:
                return FavoriteEntry.CONTENT_TYPE;
            case SPECIFIC_FAVORITE:
                return FavoriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        Cursor cursorToBeReturned;
        final int matchedUriId = uriMatcher.match(uri);

        switch (matchedUriId) {
            case FAVORITES:
                String[] actualProjection;
                if(projection == null) {
                    actualProjection = ALL_FAVORITES_PROJECTION;
                } else {
                    actualProjection = projection;
                }
                cursorToBeReturned = allFavoriteInformationQueryBuilder
                        .query(databaseHelper.getReadableDatabase(), actualProjection,
                                selection, selectionArgs, null, null, sortOrder);
                break;
            case SPECIFIC_FAVORITE:
                cursorToBeReturned = allFavoriteInformationQueryBuilder
                        .query(databaseHelper.getReadableDatabase(), ALL_FAVORITES_PROJECTION,
                                FavoriteEntry.TABLE_NAME + "." + FavoriteEntry.COLUMN_ID + " = ?",
                                new String[]{String.valueOf(ContentUris.parseId(uri))},
                                null, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return cursorToBeReturned;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int matchedUriId = uriMatcher.match(uri);
        Uri returnUri;

        switch (matchedUriId) {
            case FAVORITES:
                if (values == null) {
                    throw new RuntimeException("Favorite Information to be inserted cannot be null");
                } else {
                    returnUri = insertFavorite(values);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        ContentResolver contentResolver = getContext().getContentResolver();
        if (contentResolver != null) {
            contentResolver.notifyChange(uri, null);
        }

        return returnUri;
    }

    private Uri insertFavorite(@NonNull ContentValues values) {
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues movieTobeCachedData = extractMovieInformation(values);
        int movieId = movieTobeCachedData.getAsInteger(FavoriteMovieEntry.COLUMN_ID);
        long cachedMovieRowId = db.insert(FavoriteMovieEntry.TABLE_NAME, null, movieTobeCachedData);
        if (cachedMovieRowId < 0)
            throw new RuntimeException("There was an error registering the favorite.");

        ContentValues favoriteDataToBeInserted = new ContentValues();
        favoriteDataToBeInserted.put(FavoriteEntry.COLUMN_MOVIE_ID, movieId);

        long insertedFavoriteId = db.insert(FavoriteEntry.TABLE_NAME, null, favoriteDataToBeInserted);
        return ContentUris.withAppendedId(FavoriteEntry.CONTENT_URI, insertedFavoriteId);
    }

    private ContentValues extractMovieInformation(ContentValues values) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteMovieEntry.COLUMN_ID,
                values.getAsInteger(FavoriteMovieEntry.TABLE_NAME + "." + FavoriteMovieEntry.COLUMN_ID));

        contentValues.put(FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE,
                values.getAsString(FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE));

        contentValues.put(FavoriteMovieEntry.COLUMN_OVERVIEW,
                values.getAsString(FavoriteMovieEntry.COLUMN_OVERVIEW));

        contentValues.put(FavoriteMovieEntry.COLUMN_POPULARITY,
                values.getAsDouble(FavoriteMovieEntry.COLUMN_POPULARITY));

        contentValues.put(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE,
                values.getAsDouble(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE));

        contentValues.put(FavoriteMovieEntry.COLUMN_POSTER_PATH,
                values.getAsString(FavoriteMovieEntry.COLUMN_POSTER_PATH));

        contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE,
                values.getAsString(FavoriteMovieEntry.COLUMN_RELEASE_DATE));

        return contentValues;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int matchedUriId = uriMatcher.match(uri);
        int rowsDeleted;

        switch (matchedUriId) {
            case FAVORITES:
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.delete(FavoriteMovieEntry.TABLE_NAME, null, null);
                rowsDeleted = db.delete(FavoriteEntry.TABLE_NAME, null, null);
                break;
            case SPECIFIC_FAVORITE:
                rowsDeleted = deleteSpecificFavorite(uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rowsDeleted != 0) {
            ContentResolver contentResolver = getContext().getContentResolver();
            if (contentResolver != null) {
                contentResolver.notifyChange(uri, null);
            }
        }

        return rowsDeleted;
    }

    private int deleteSpecificFavorite(@NonNull Uri uri) {
        int rowsDeleted = 0;
        final SQLiteDatabase db = databaseHelper.getWritableDatabase();

        final String movieIdTobeDeleted = String.valueOf(ContentUris.parseId(uri));

        int cachedMoviesDeleted = db.delete(FavoriteMovieEntry.TABLE_NAME,
                FavoriteMovieEntry.COLUMN_ID + " = ? ",
                new String[]{ movieIdTobeDeleted });

        rowsDeleted = db.delete(FavoriteEntry.TABLE_NAME,
                FavoriteEntry.COLUMN_MOVIE_ID + " = ? ",
                new String[]{ movieIdTobeDeleted });

        if (cachedMoviesDeleted != rowsDeleted) {
            throw new RuntimeException("Error deleting favorite, inconstency between deletion of cached movie and favorite: " + uri);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
}
