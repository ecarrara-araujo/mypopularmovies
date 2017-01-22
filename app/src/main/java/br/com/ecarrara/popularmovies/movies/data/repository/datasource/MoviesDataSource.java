package br.com.ecarrara.popularmovies.movies.data.repository.datasource;

import java.util.List;

import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MoviesDataSource {
    Single<List<Movie>> listPopularMovies();
    Single<List<Movie>> listTopRatedMovies();
}
