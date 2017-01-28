package br.com.ecarrara.popularmovies.movies.presentation;

import br.com.ecarrara.popularmovies.core.presentation.LoadDataView;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;

public interface MovieDetailView extends LoadDataView {

    String MOVIE_ID_KEY = "MOVIE_ID";
    int NO_MOVIE_ID = -1;

    void displayMovieDetail(MovieDetailViewModel movieDetailViewModel);
}
