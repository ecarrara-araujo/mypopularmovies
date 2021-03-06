package br.com.ecarrara.popularmovies.trailers.domain;

import java.util.List;

import br.com.ecarrara.popularmovies.trailers.domain.entity.Trailer;
import io.reactivex.Single;

public interface TrailersRepository {
    Single<List<Trailer>> listMovieTrailers(int movieId);
}
