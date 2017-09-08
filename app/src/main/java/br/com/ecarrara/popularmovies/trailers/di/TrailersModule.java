package br.com.ecarrara.popularmovies.trailers.di;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.trailers.data.TrailersRepositoryImpl;
import br.com.ecarrara.popularmovies.trailers.data.datasource.TrailersDataSource;
import br.com.ecarrara.popularmovies.trailers.data.datasource.rest.TrailersRestApiDataSource;
import br.com.ecarrara.popularmovies.trailers.domain.TrailersRepository;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class TrailersModule {

    @Provides
    @Singleton
    public TrailersRepository providesTrailersRepository(TrailersDataSource trailersDataSource) {
        return new TrailersRepositoryImpl(trailersDataSource);
    }

    @Provides
    @Singleton
    public TrailersDataSource providesTrailersDataSource() {
        return new TrailersRestApiDataSource();
    }

}
