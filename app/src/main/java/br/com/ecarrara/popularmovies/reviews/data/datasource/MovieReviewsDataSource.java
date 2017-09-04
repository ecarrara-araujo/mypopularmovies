package br.com.ecarrara.popularmovies.reviews.data.datasource;

import java.util.List;

import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;
import io.reactivex.Single;

public interface MovieReviewsDataSource {

    Single<List<MovieReview>> listMovieReviews(int movieId);

}
