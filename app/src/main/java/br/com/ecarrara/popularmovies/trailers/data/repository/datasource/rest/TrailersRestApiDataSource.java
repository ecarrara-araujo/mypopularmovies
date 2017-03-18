package br.com.ecarrara.popularmovies.trailers.data.repository.datasource.rest;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.trailers.data.repository.datasource.TrailersDataSource;
import br.com.ecarrara.popularmovies.trailers.data.repository.datasource.rest.json.MovieTrailersResponse;
import br.com.ecarrara.popularmovies.trailers.data.repository.datasource.rest.mapper.RestTrailerMapper;
import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;
import io.reactivex.Single;

public class TrailersRestApiDataSource implements TrailersDataSource {

    private TrailersRestApi trailersRestApi;

    public TrailersRestApiDataSource() {
        this(new RestApiConnection(RestConfigs.SERVER_URL));
    }

    public TrailersRestApiDataSource(RestApiConnection restApiConnection) {
        this.trailersRestApi = restApiConnection.connectTo(TrailersRestApi.class);
    }

    @Override
    public Single<List<Trailer>> listMovieTrailers(int movieId) {
        return trailersRestApi
                .getMovieTrailers(movieId)
                .flatMapIterable(MovieTrailersResponse::results)
                .map(RestTrailerMapper::transformFrom)
                .toList();
    }

}
