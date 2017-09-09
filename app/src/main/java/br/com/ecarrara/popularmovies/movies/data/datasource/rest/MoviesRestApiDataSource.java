package br.com.ecarrara.popularmovies.movies.data.datasource.rest;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.movies.data.datasource.MoviesDataSource;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.json.Response;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.mapper.RestMovieMapper;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Single;

public class MoviesRestApiDataSource implements MoviesDataSource {

    private MoviesRestApi moviesRestApi;

    public MoviesRestApiDataSource() {
        this(new RestApiConnection(RestConfigs.SERVER_URL));
    }

    public MoviesRestApiDataSource(RestApiConnection restApiConnection) {
        this.moviesRestApi = restApiConnection.connectTo(MoviesRestApi.class);
    }

    @Override
    public Single<List<Movie>> listPopularMovies() {
        return moviesRestApi
                .getPopularMovies()
                .flatMapIterable(Response::results)
                .map(RestMovieMapper::transformFrom)
                .toList();
    }

    @Override
    public Single<List<Movie>> listTopRatedMovies() {
        return moviesRestApi
                .getTopRatedMovies()
                .flatMapIterable(Response::results)
                .map(RestMovieMapper::transformFrom)
                .toList();
    }

    @Override
    public Single<Movie> getMovieDetail(int movieId) {
        return moviesRestApi
                .getMovieDetail(movieId)
                .map(RestMovieMapper::transformFrom);
    }
}
