package br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest;

import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json.Response;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface MoviesRestApi {

    @GET("movie/popular")
    Observable<Response> getPopularMovies();

    @GET("movie/top_rated")
    Observable<Response> getTopRatedMovies();

}
