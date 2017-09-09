package br.com.ecarrara.popularmovies.movies.di;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.movies.data.MoviesRepositoryImpl;
import br.com.ecarrara.popularmovies.movies.data.datasource.MoviesDataSource;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.MoviesRestApiDataSource;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class MoviesModule {

    @Provides
    @Singleton
    public MoviesRepository providesMovieRepository(MoviesDataSource moviesDataSource) {
        return new MoviesRepositoryImpl(moviesDataSource);
    }

    @Provides
    @Singleton
    public MoviesDataSource providesMoviesDataSource() {
        return new MoviesRestApiDataSource();
    }

}
