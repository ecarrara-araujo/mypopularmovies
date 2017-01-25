package br.com.ecarrara.popularmovies.movies.presentation.model;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestImageUrlBuilder;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

public class MovieListItemViewModelMapper {

    private static MovieListItemViewModel transformFrom(Movie movie) {
        return MovieListItemViewModel.create(movie.id(),
                RestImageUrlBuilder.build(movie.posterPath()));
    }

    public static List<MovieListItemViewModel> transformFrom(List<Movie> movies) {
        List<MovieListItemViewModel> movieListItemViewModels = new ArrayList<>(movies.size());
        for (Movie movie: movies) {
            movieListItemViewModels.add(transformFrom(movie));
        }
        return movieListItemViewModels;
    }
}
