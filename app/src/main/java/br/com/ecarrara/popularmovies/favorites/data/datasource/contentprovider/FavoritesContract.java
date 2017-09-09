package br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider;

import android.net.Uri;

import static br.com.ecarrara.popularmovies.core.data.datasource.contentprovider.ContentProviderContract.BASE_CONTENT_URI;
import static br.com.ecarrara.popularmovies.core.data.datasource.contentprovider.ContentProviderContract.CONTENT_AUTHORITY;

public final class FavoritesContract {

    private FavoritesContract() { /* Must not be instantiated */ }

    public static final String PATH_FAVORITE = "favorite";

    public static final class FavoriteEntry {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE;

        public static final String TABLE_NAME = "favorite";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_MOVIE_ID = "movie_id";

        public static final String SQL_CREATE_FAVORITE_TABLE =
                "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                        FavoriteEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                        FavoriteEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                        "FOREIGN KEY(" + FavoriteEntry.COLUMN_MOVIE_ID +
                        ") REFERENCES " + FavoriteMovieEntry.TABLE_NAME + " ( " +
                        FavoriteMovieEntry.COLUMN_ID + ")" +
                        ");";

    }

    public static final class FavoriteMovieEntry {

        public static final String TABLE_NAME = "favorite_movie";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_OVERVIEW = "overview";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_POPULARITY = "popularity";

        public static final String COLUMN_VOTE_AVERAGE = "vote_average";

        public static final String COLUMN_POSTER_PATH = "poster_path";

        public static final String SQL_CREATE_FAVORITE_MOVIE_TABLE =
                "CREATE TABLE " + FavoriteMovieEntry.TABLE_NAME + " (" +
                        FavoriteMovieEntry.COLUMN_ID + " INTEGER UNIQUE, " +
                        FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        FavoriteMovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                        FavoriteMovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                        FavoriteMovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                        FavoriteMovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                        FavoriteMovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL " +
                        ");";

    }

}
