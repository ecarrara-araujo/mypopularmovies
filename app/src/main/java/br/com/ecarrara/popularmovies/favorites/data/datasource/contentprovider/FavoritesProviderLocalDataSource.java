package br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.Collections;
import java.util.List;

import br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider.FavoritesContract.FavoriteEntry;
import br.com.ecarrara.popularmovies.favorites.domain.data.FavoritesLocalDataSource;
import br.com.ecarrara.popularmovies.favorites.domain.entity.Favorite;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Completable;
import io.reactivex.Single;

public class FavoritesProviderLocalDataSource implements FavoritesLocalDataSource {

    private Context applicationContext;

    public FavoritesProviderLocalDataSource(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Completable save(Movie movie) {
        return Completable.defer(() -> {
            ContentValues data = FavoriteProviderMapper.mapContentValuesFrom(movie);
            Uri insertedFavoriteUri = applicationContext.getContentResolver()
                    .insert(FavoriteEntry.CONTENT_URI, data);

            if (insertedFavoriteUri != null) {
                return Completable.complete();
            } else {
                return Completable.error(new RuntimeException("It was not possible to save Favorite"));
            }
        });
    }

    @Override
    public Completable delete(Movie movie) {
        return Completable.defer(() -> {
            int numberOfDeletedFavorites = applicationContext.getContentResolver().delete(
                    ContentUris.withAppendedId(FavoriteEntry.CONTENT_URI, movie.id()),
                    null, null
            );
            if (numberOfDeletedFavorites == 1) {
                return Completable.complete();
            } else {
                return Completable.error(new RuntimeException("Error deleting favorite"));
            }
        });
    }

    @Override
    public Single<List<Movie>> list() {
        return Single.defer(() -> {
            Cursor favoritesCursor = applicationContext.getContentResolver().query(
                    FavoriteEntry.CONTENT_URI, null, null, null, null);

            if (favoritesCursor == null) {
                return Single.just(Collections.emptyList());
            }

            List<Movie> favoriteMovies = FavoriteProviderMapper.mapMoviesFromCursor(favoritesCursor);
            return Single.just(favoriteMovies);
        });
    }

    @Override
    public Single<Boolean> isFavorite(Movie movie) {
        return Single.defer(() -> {
            Cursor cursor = applicationContext.getContentResolver().query(FavoriteEntry.CONTENT_URI,
                    null,
                    FavoriteEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{String.valueOf(movie.id())},
                    null
            );

            boolean isFavorite = false;
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    isFavorite = true;
                }
                cursor.close();
            }
            return Single.just(isFavorite);
        });
    }

}
