package br.com.ecarrara.popularmovies.movies.presentation.model;

import com.google.auto.value.AutoValue;


@AutoValue public abstract class MovieListItemViewModel {

    public abstract Integer movieId();
    public abstract String posterPath();
    public abstract String title();

    public static MovieListItemViewModel create(int movieId, String posterPath, String title) {
        return new AutoValue_MovieListItemViewModel(movieId, posterPath, title);
    }

}
