package br.com.ecarrara.popularmovies.trailers.data.datasource.rest;

import br.com.ecarrara.popularmovies.trailers.data.datasource.rest.json.MovieTrailersResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrailersRestApi {
    @GET("movie/{movieId}/videos")
    Observable<MovieTrailersResponse> getMovieTrailers(@Path("movieId") int movieId);
}
