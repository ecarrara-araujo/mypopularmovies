package br.com.ecarrara.popularmovies.reviews.presentation.view;

import java.util.List;

import br.com.ecarrara.popularmovies.core.presentation.LoadDataView;
import br.com.ecarrara.popularmovies.reviews.presentation.model.MovieReviewListItemViewModel;

public interface MovieReviewsListView extends LoadDataView {
    void displayMovieReviewsList(List<MovieReviewListItemViewModel> movieReviewsList);
    void expandMovieReview(int index);
}
