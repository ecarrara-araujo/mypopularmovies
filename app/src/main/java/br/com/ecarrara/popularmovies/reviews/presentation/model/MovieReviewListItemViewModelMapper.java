package br.com.ecarrara.popularmovies.reviews.presentation.model;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;

public class MovieReviewListItemViewModelMapper {

    private static MovieReviewListItemViewModel transformFrom(MovieReview movieReview) {
        return MovieReviewListItemViewModel.create(movieReview.author(), movieReview.content());
    }

    public static List<MovieReviewListItemViewModel> transformFrom(List<MovieReview> movieReviews) {
        List<MovieReviewListItemViewModel> transformedMovieReviewsListItemModels =
                new ArrayList<>(movieReviews.size());
        for(MovieReview movieReview : movieReviews) {
            transformedMovieReviewsListItemModels.add(MovieReviewListItemViewModel.create(
                    movieReview.author(), movieReview.content()
            ));
        }
        return transformedMovieReviewsListItemModels;
    }
}
