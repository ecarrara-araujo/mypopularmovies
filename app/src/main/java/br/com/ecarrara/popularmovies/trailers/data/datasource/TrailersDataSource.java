package br.com.ecarrara.popularmovies.trailers.data.datasource;

import java.util.List;

import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;
import io.reactivex.Single;

public interface TrailersDataSource {
    Single<List<Trailer>> listMovieTrailers(int movieId);
}
