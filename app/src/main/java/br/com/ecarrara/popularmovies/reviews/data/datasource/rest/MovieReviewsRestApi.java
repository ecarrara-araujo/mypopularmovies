package br.com.ecarrara.popularmovies.reviews.data.datasource.rest;

import br.com.ecarrara.popularmovies.reviews.data.datasource.rest.json.MovieReviewsResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieReviewsRestApi {
    @GET("movie/{movieId}/reviews")
    Observable<MovieReviewsResponse> getMovieReviews(@Path("movieId") int movieId);
}
