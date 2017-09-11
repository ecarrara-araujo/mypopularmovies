package br.com.ecarrara.popularmovies.movies.data;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.connectivity.ConnectivityObserver;
import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.favorites.domain.data.FavoritesLocalDataSource;
import br.com.ecarrara.popularmovies.movies.data.datasource.MoviesDataSource;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.MoviesRestApiDataSource;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Single;

public class MoviesRepositoryImpl implements MoviesRepository {

    private MoviesDataSource moviesDataSource;
    private FavoritesLocalDataSource favoritesLocalDataSource;
    private ConnectivityObserver connectivityObserver;

    public MoviesRepositoryImpl(
            MoviesDataSource moviesDataSource,
            FavoritesLocalDataSource favoritesLocalDataSource,
            ConnectivityObserver connectivityObserver
    ) {
        this.moviesDataSource = moviesDataSource;
        this.favoritesLocalDataSource = favoritesLocalDataSource;
        this.connectivityObserver = connectivityObserver;
    }

    public Single<List<Movie>> listPopularMovies() {
        return this.moviesDataSource.listPopularMovies();
    }

    public Single<List<Movie>> listTopRatedMovies() {
        return this.moviesDataSource.listTopRatedMovies();
    }

    @Override
    public Single<Movie> getMovieDetail(int movieId) {
        return this.connectivityObserver.observeConnectivity()
                .firstOrError()
                .flatMap(isConnected -> {
                    if (isConnected) {
                        return this.moviesDataSource.getMovieDetail(movieId);
                    } else {
                        return this.favoritesLocalDataSource.getFavoriteMovieDetail(movieId);
                    }
                });
    }

}
