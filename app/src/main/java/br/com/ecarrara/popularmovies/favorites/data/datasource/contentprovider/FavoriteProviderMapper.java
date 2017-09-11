package br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.ecarrara.popularmovies.core.data.datasource.contentprovider.ContentProviderContract;
import br.com.ecarrara.popularmovies.core.utils.datetime.DateUtils;
import br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider.FavoritesContract.FavoriteMovieEntry;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

public final class FavoriteProviderMapper {

    private FavoriteProviderMapper() { /* Cannot be instantiated */ }

    static List<Movie> mapMoviesFromCursor(@NonNull Cursor cursor) {
        if (!cursor.moveToFirst()) {
            return Collections.emptyList();
        }

        List<Movie> mappedFavoriteMovies = new ArrayList<>(cursor.getCount());

        while (!cursor.isAfterLast()) {
            Movie mappedFavoriteMovie = extractMovieFromCursor(cursor);
            mappedFavoriteMovies.add(mappedFavoriteMovie);
            cursor.moveToNext();
        }

        return mappedFavoriteMovies;
    }

    static Movie mapMovieFromCursor(@NonNull Cursor cursor) {
        if (!cursor.moveToFirst()) {
            throw new RuntimeException("Error mapping cached favorite Movie from cursor to object");
        }
        return extractMovieFromCursor(cursor);
    }

    private static Movie extractMovieFromCursor(@NonNull Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_ID));
        String originalTitle = cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE));
        String overview = cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_OVERVIEW));
        Double popularity = cursor.getDouble(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POPULARITY));
        Double voteAverage = cursor.getDouble(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE));
        String posterPath = cursor.getString(cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_POSTER_PATH));
        Date releaseDate = mapDateFromDatabaseString(cursor.getString(
                cursor.getColumnIndex(FavoriteMovieEntry.COLUMN_RELEASE_DATE)));

        return new Movie
                .Builder(id, originalTitle)
                .setOverview(overview)
                .setPopularity(popularity)
                .setPosterPath(posterPath)
                .setReleaseDate(releaseDate)
                .setVoteAverage(voteAverage)
                .build();
    }

    private static Date mapDateFromDatabaseString(String dateString) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(ContentProviderContract.DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return DateUtils.INVALID_DATE;
        }
    }

    private static String mapDatabaseStringFromDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ContentProviderContract.DATE_FORMAT);
        return sdf.format(date);
    }

    static ContentValues mapContentValuesFrom(Movie movie) {
        ContentValues contentValues = new ContentValues();
        if (movie.id() != Movie.INVALID_ID) {
            contentValues.put(FavoriteMovieEntry.TABLE_NAME + "." + FavoriteMovieEntry.COLUMN_ID, movie.id());
        }

        contentValues.put(FavoriteMovieEntry.COLUMN_ORIGINAL_TITLE, movie.originalTitle());
        contentValues.put(FavoriteMovieEntry.COLUMN_OVERVIEW, movie.overview());
        contentValues.put(FavoriteMovieEntry.COLUMN_RELEASE_DATE, mapDatabaseStringFromDate(movie.releaseDate()));
        contentValues.put(FavoriteMovieEntry.COLUMN_POPULARITY, movie.popularity());
        contentValues.put(FavoriteMovieEntry.COLUMN_VOTE_AVERAGE, movie.voteAverage());
        contentValues.put(FavoriteMovieEntry.COLUMN_POSTER_PATH, movie.posterPath());

        return contentValues;
    }

}
