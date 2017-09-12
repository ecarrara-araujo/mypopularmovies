package br.com.ecarrara.popularmovies.movies.data.datasource.rest.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestDateMapper;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.json.MovieDetailResponse;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.json.Result;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

public class RestMovieMapper {

    public static Movie transformFrom(Result restMovieResult) {
        return Movie.builder()
                .setId(restMovieResult.id())
                .setOriginalTitle(restMovieResult.originalTitle())
                .setOverview(restMovieResult.overview())
                .setPopularity(restMovieResult.popularity())
                .setPosterPath(restMovieResult.posterPath())
                .setReleaseDate(RestDateMapper.transformFrom(restMovieResult.releaseDate()))
                .setVoteAverage(restMovieResult.voteAverage())
                .build();
    }

    public static Movie transformFrom(MovieDetailResponse movieDetailResponse) {
        return Movie.builder()
                .setId(movieDetailResponse.id())
                .setOriginalTitle(movieDetailResponse.originalTitle())
                .setOverview(movieDetailResponse.overview())
                .setPopularity(movieDetailResponse.popularity())
                .setPosterPath(movieDetailResponse.posterPath())
                .setReleaseDate(RestDateMapper.transformFrom(movieDetailResponse.releaseDate()))
                .setVoteAverage(movieDetailResponse.voteAverage())
                .build();
    }

    public static List<Movie> transformFrom(List<Result> restMoviesList) {
        List<Movie> transformedMovies = new ArrayList<>(restMoviesList.size());
        for (Result movieResult : restMoviesList) {
            transformedMovies.add(transformFrom(movieResult));
        }
        return transformedMovies;
    }

}
