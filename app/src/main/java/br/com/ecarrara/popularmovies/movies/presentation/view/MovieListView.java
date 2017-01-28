package br.com.ecarrara.popularmovies.movies.presentation.view;

import java.util.List;

import br.com.ecarrara.popularmovies.core.presentation.LoadDataView;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;

public interface MovieListView extends LoadDataView {
    void displayMoviesList(List<MovieListItemViewModel> movieListItemModelList);
    void navigateToMovieDetailScreen(Integer movieId);
}
