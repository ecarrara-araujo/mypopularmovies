package br.com.ecarrara.popularmovies.movies.data.datasource;

import java.util.List;

import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Single;

public interface MoviesDataSource {
    Single<List<Movie>> listPopularMovies();
    Single<List<Movie>> listTopRatedMovies();
    Single<Movie> getMovieDetail(int movieId);
}
