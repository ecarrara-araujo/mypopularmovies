package br.com.ecarrara.popularmovies.trailers.data.repository;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.trailers.data.repository.datasource.TrailersDataSource;
import br.com.ecarrara.popularmovies.trailers.data.repository.datasource.rest.TrailersRestApiDataSource;
import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;
import io.reactivex.Single;

public class TrailersRepositoryImpl implements TrailersRepository {

    private TrailersDataSource trailersDataSource;

    public TrailersRepositoryImpl() {
        this.trailersDataSource =
                new TrailersRestApiDataSource(new RestApiConnection(RestConfigs.SERVER_URL));
    }

    public TrailersRepositoryImpl(TrailersDataSource trailersDataSource) {
        this.trailersDataSource = trailersDataSource;
    }

    @Override
    public Single<List<Trailer>> listMovieTrailers(int movieId) {
        return this.trailersDataSource.listMovieTrailers(movieId);
    }
}
