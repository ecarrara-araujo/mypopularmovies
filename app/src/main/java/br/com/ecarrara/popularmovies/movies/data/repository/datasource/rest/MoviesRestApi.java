package br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest;

import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json.MovieDetailResponse;
import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json.Response;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MoviesRestApi {

    @GET("movie/popular")
    Observable<Response> getPopularMovies();

    @GET("movie/top_rated")
    Observable<Response> getTopRatedMovies();

    @GET("movie/{movieId}")
    Single<MovieDetailResponse> getMovieDetail(@Path("movieId") int movieId);

}
