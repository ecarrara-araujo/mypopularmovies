package br.com.ecarrara.popularmovies.reviews.data.datasource.rest;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.reviews.data.datasource.MovieReviewsDataSource;
import br.com.ecarrara.popularmovies.reviews.data.datasource.rest.json.MovieReviewsResponse;
import br.com.ecarrara.popularmovies.reviews.data.datasource.rest.mapper.RestReviewMapper;
import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;
import io.reactivex.Single;

public class MovieReviewsRestApiDataSource implements MovieReviewsDataSource {

    private MovieReviewsRestApi movieReviewsRestApi;

    public MovieReviewsRestApiDataSource()  {
        this(new RestApiConnection(RestConfigs.SERVER_URL));
    }

    public MovieReviewsRestApiDataSource(RestApiConnection restApiConnection) {
        this.movieReviewsRestApi = restApiConnection.connectTo(MovieReviewsRestApi.class);
    }

    @Override
    public Single<List<MovieReview>> listMovieReviews(int movieId) {
        return movieReviewsRestApi
                .getMovieReviews(movieId)
                .flatMapIterable(MovieReviewsResponse::results)
                .map(RestReviewMapper::transformFrom)
                .toList();
    }
}
