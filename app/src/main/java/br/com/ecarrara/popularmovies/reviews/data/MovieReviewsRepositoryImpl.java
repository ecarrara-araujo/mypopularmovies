package br.com.ecarrara.popularmovies.reviews.data;

import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.core.networking.rest.RestConfigs;
import br.com.ecarrara.popularmovies.reviews.data.datasource.MovieReviewsDataSource;
import br.com.ecarrara.popularmovies.reviews.data.datasource.rest.MovieReviewsRestApiDataSource;
import br.com.ecarrara.popularmovies.reviews.domain.MovieReviewsRepository;
import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;
import io.reactivex.Single;

public class MovieReviewsRepositoryImpl implements MovieReviewsRepository {

    private MovieReviewsDataSource movieReviewsDataSource;

    public MovieReviewsRepositoryImpl(MovieReviewsDataSource movieReviewsDataSource) {
        this.movieReviewsDataSource = movieReviewsDataSource;
    }

    @Override
    public Single<List<MovieReview>> listMovieReviews(int movieId) {
        return this.movieReviewsDataSource.listMovieReviews(movieId);
    }
}
