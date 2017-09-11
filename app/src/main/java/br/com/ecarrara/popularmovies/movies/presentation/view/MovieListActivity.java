package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.core.di.Injector;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.presenter.MoviesListPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MovieListActivity extends AppCompatActivity
        implements MovieListView, MovieListAdapter.MovieSelectedListener {

    @Inject MoviesListPresenter moviesListPresenter;

    private MovieListAdapter movieListAdapter;

    @BindView(R.id.recycler_view_movie_list) RecyclerView movieListView;
    @BindView(R.id.progress_indicator) ProgressBar progressIndicator;
    @BindView(R.id.text_view_error_message) TextView errorDisplay;
    @BindView(R.id.button_retry) ImageButton retryButton;
    @BindView(R.id.bottom_navigation_menu_movies_filtering) BottomNavigationView moviesFilteringBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        Injector.applicationComponent().inject(this);
        setupRecyclerView();
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        moviesFilteringBottomNavigationView.setSelectedItemId(R.id.menu_action_load_most_popular);
        moviesFilteringBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_action_load_most_popular:
                    this.moviesListPresenter.onListPopularMovies();
                    break;
                case R.id.menu_action_load_top_rated:
                    this.moviesListPresenter.onListTopRatedMovies();
                    break;
                case R.id.menu_action_load_favorites:
                    this.moviesListPresenter.onListFavorites();
                    break;
            }
            return true;
        });
    }

    private void setupRecyclerView() {
        final int NUMBER_OF_COLUMNS_IN_GRID = 2;

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS_IN_GRID);
        movieListAdapter = new MovieListAdapter(MovieListActivity.this, MovieListActivity.this);
        movieListView.setAdapter(movieListAdapter);
        movieListView.setLayoutManager(layoutManager);
        movieListView.setHasFixedSize(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.moviesListPresenter.attachTo(this);
    }

    @Override
    protected void onDestroy() {
        this.moviesListPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void displayMoviesList(List<MovieListItemViewModel> movieListItemModelList) {
        this.showContent();
        this.movieListAdapter.setMovieListItemViewModels(movieListItemModelList);
    }

    @Override
    public void navigateToMovieDetailScreen(Integer movieId) {
        Intent movieDetailIntent = new Intent(MovieListActivity.this, MovieDetailActivity.class);
        movieDetailIntent.putExtra(MovieDetailView.MOVIE_ID_KEY, movieId);
        startActivity(movieDetailIntent);
    }

    @Override
    public void showLoading() {
        hideError();
        hideRetry();
        hideContent();
        progressIndicator.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressIndicator.setVisibility(GONE);
    }

    @Override
    public void showRetry() {
        retryButton.setVisibility(VISIBLE);
    }

    @Override
    public void hideRetry() {
        retryButton.setVisibility(GONE);
    }

    @Override
    public void showError(String message) {
        hideLoading();
        hideContent();
        errorDisplay.setVisibility(VISIBLE);
        errorDisplay.setText(message);
    }

    @Override
    public void hideError() {
        errorDisplay.setVisibility(GONE);
    }

    private void hideContent() {
        movieListView.setVisibility(GONE);
    }

    private void showContent() {
        hideLoading();
        hideError();
        hideRetry();
        movieListView.setVisibility(VISIBLE);
    }

    @Override
    public void onMovieSelected(Integer movieId) {
        this.moviesListPresenter.onMovieSelected(movieId);
    }
}
