package br.com.ecarrara.popularmovies.movies.presentation.model;

import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

public class MovieDetailViewModelMapper {
    public static MovieDetailViewModel transformFrom(Movie movie) {
        return new MovieDetailViewModel.Builder(movie.originalTitle())
                .setDate(movie.releaseDate())
                .setPosterPath(movie.posterPath())
                .setVoteAverage(movie.voteAverage())
                .setPlotSynopsis(movie.overview())
                .build();
    }
}
