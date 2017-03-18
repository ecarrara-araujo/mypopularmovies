package br.com.ecarrara.popularmovies.reviews.data.repository;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.reviews.data.repository.datasource.MovieReviewsDataSource;
import br.com.ecarrara.popularmovies.reviews.data.repository.datasource.rest.MovieReviewsRestApiDataSource;
import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;
import io.reactivex.Single;

public class MovieReviewsRepositoryImpl implements MovieReviewsRepository {

    private MovieReviewsDataSource movieReviewsDataSource;

    public MovieReviewsRepositoryImpl()  {
        this.movieReviewsDataSource =
                new MovieReviewsRestApiDataSource(new RestApiConnection(RestConfigs.SERVER_URL));
    }

    public MovieReviewsRepositoryImpl(MovieReviewsDataSource movieReviewsDataSource) {
        this.movieReviewsDataSource = movieReviewsDataSource;
    }

    @Override
    public Single<List<MovieReview>> listMovieReviews(int movieId) {
        return this.movieReviewsDataSource.listMovieReviews(movieId);
    }
}
