package br.com.ecarrara.popularmovies.movies.presentation.view;

import br.com.ecarrara.popularmovies.core.presentation.LoadDataView;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;

public interface MovieDetailView extends LoadDataView {
    String MOVIE_ID_KEY = "MOVIE_ID";
    void displayMovieDetail(MovieDetailViewModel movieDetailViewModel);
}
