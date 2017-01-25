package br.com.ecarrara.popularmovies.movies.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MovieListActivity extends AppCompatActivity implements MovieListView {

    private MoviesListPresenter moviesListPresenter;
    private MovieListAdapter movieListAdapter;

    private RecyclerView movieListView;
    private ProgressBar progressIndicator;
    private TextView errorDisplay;
    private ImageButton retryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list_activity);
        initialize();
    }

    private void initialize() {
        this.moviesListPresenter = new MoviesListPresenter();
        setupRecyclerView();
        progressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);
        errorDisplay = (TextView) findViewById(R.id.text_view_error_message);
        retryButton = (ImageButton) findViewById(R.id.button_retry);
    }

    private void setupRecyclerView() {
        final int NUMBER_OF_COLUMNS_IN_GRID = 2;
        movieListView = (RecyclerView) findViewById(R.id.recycler_view_movie_list);

        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_COLUMNS_IN_GRID);
        movieListAdapter = new MovieListAdapter(MovieListActivity.this);
        movieListView.setAdapter(movieListAdapter);
        movieListView.setLayoutManager(layoutManager);
        movieListView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_list_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.moviesListPresenter.attachTo(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int itemId = item.getItemId();

        switch (itemId) {
            case (R.id.menu_action_load_most_popular):
                this.moviesListPresenter.onListPopularMovies();
                break;
            case(R.id.menu_action_load_top_rated):
                this.moviesListPresenter.onListTopRatedMovies();
                break;
            default:
                this.showError("How on Earth did you got here?");
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void displayMoviesList(List<MovieListItemViewModel> movieListItemModelList) {
        this.showContent();
        this.movieListAdapter.setMovieListItemViewModels(movieListItemModelList);
    }

    @Override
    public void navigateToMovieDetailScreen(Integer movieId) {

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
}
