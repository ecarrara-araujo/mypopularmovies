package br.com.ecarrara.popularmovies.movies.presentation.model;

import java.text.SimpleDateFormat;

import br.com.ecarrara.popularmovies.core.networking.rest.RestImageUrlBuilder;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

public class MovieDetailViewModelMapper {
    public static MovieDetailViewModel transformFrom(Movie movie) {
        return transformFrom(movie, false);
    }

    public static MovieDetailViewModel transformFrom(Movie movie, boolean isFavorite) {
        return MovieDetailViewModel.builder()
                .setTitle(movie.originalTitle())
                .setReleaseDate(SimpleDateFormat.getDateInstance().format(movie.releaseDate()))
                .setPosterPath(RestImageUrlBuilder.buildPosterUrl(movie.posterPath()))
                .setBackdropPath(RestImageUrlBuilder.buildBackdropUrl(movie.backdropPath()))
                .setVoteAverage(movie.voteAverage())
                .setPlotSynopsis(movie.overview())
                .setIsFavorite(isFavorite)
                .build();
    }
}
