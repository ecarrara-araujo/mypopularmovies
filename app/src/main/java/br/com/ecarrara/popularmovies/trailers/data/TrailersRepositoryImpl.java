package br.com.ecarrara.popularmovies.trailers.data;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.trailers.data.datasource.TrailersDataSource;
import br.com.ecarrara.popularmovies.trailers.data.datasource.rest.TrailersRestApiDataSource;
import br.com.ecarrara.popularmovies.trailers.domain.TrailersRepository;
import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;
import io.reactivex.Single;

public class TrailersRepositoryImpl implements TrailersRepository {

    private TrailersDataSource trailersDataSource;

    public TrailersRepositoryImpl(TrailersDataSource trailersDataSource) {
        this.trailersDataSource = trailersDataSource;
    }

    @Override
    public Single<List<Trailer>> listMovieTrailers(int movieId) {
        return this.trailersDataSource.listMovieTrailers(movieId);
    }
}
