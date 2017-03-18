package br.com.ecarrara.popularmovies.reviews.data.repository;

import java.util.List;

import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;
import io.reactivex.Single;

public interface MovieReviewsRepository {
    Single<List<MovieReview>> listMovieReviews(int movieId);
}
