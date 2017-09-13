package br.com.ecarrara.popularmovies.favorites.domain.entity;

import com.google.auto.value.AutoValue;

import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

@AutoValue
public abstract class Favorite {

    public static final int INVALID_ID = -1;

    public abstract int id();

    public abstract String addedDate();

    public abstract Movie movie();

    public static Favorite create(int id, String addedDate, Movie favoriteMovie) {
        return new AutoValue_Favorite(id, addedDate, favoriteMovie);
    }

}
