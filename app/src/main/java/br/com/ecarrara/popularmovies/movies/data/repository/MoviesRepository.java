package br.com.ecarrara.popularmovies.movies.data.repository;

import java.util.List;

import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Single;

public interface MoviesRepository {
    Single<List<Movie>> listPopularMovies();
    Single<List<Movie>> listTopRatedMovies();
    Single<Movie> getMovieDetail(int movieId);
}
