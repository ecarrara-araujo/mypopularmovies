package br.com.ecarrara.popularmovies.movies.presentation.model;

import com.google.auto.value.AutoValue;


@AutoValue public abstract class MovieListItemViewModel {

    public abstract Integer movieId();
    public abstract String posterPath();

    public static MovieListItemViewModel create(int movieId, String posterPath) {
        return new AutoValue_MovieListItemViewModel(movieId, posterPath);
    }

}
