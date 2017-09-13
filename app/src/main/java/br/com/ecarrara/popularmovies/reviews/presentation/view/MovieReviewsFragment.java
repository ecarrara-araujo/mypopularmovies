package br.com.ecarrara.popularmovies.reviews.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.core.di.Injector;
import br.com.ecarrara.popularmovies.reviews.presentation.model.MovieReviewListItemViewModel;
import br.com.ecarrara.popularmovies.reviews.presentation.presenter.MovieReviewsListPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MovieReviewsFragment extends Fragment
    implements MovieReviewsListView, MovieReviewsAdapter.MovieReviewSelectedListener {

    @Inject MovieReviewsListPresenter movieReviewsListPresenter;

    @BindView(R.id.recycler_view_movie_reviews_list) RecyclerView movieReviesListView;
    @BindView(R.id.progress_indicator) ProgressBar progressIndicator;
    @BindView(R.id.text_view_error_message) TextView errorDisplay;
    @BindView(R.id.button_retry) ImageButton retryButton;

    private static final String ARGUMENT_MOVIE_ID = "movie_id";
    private static final int INVALID_MOVIE_ID = -1;

    private static final String LAST_KNOWN_REVIEWS_LIST_POSITION_KEY = "last_known_movie_list_position";
    private static final int DEFAULT_REVIEWS_LIST_INITIAL_POSITION = 0;
    private int lastKnownReviewsListPosition = DEFAULT_REVIEWS_LIST_INITIAL_POSITION;

    private MovieReviewsAdapter movieReviewsAdapter;

    private int movieId = INVALID_MOVIE_ID;

    public MovieReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new Movie Reviews List for the informed movieId
     * @param movieId
     * @return
     */
    public static MovieReviewsFragment newInstance(int movieId) {
        MovieReviewsFragment movieReviewsFragment = new MovieReviewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_MOVIE_ID, movieId);
        movieReviewsFragment.setArguments(args);
        return movieReviewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processSavedInstanceState(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARGUMENT_MOVIE_ID);
        }
    }

    private void processSavedInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            lastKnownReviewsListPosition = savedInstanceState.getInt(
                    LAST_KNOWN_REVIEWS_LIST_POSITION_KEY, DEFAULT_REVIEWS_LIST_INITIAL_POSITION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.movie_reviews_list_fragment, container, false);
        ButterKnife.bind(this, inflatedView);
        initialize();

        if(movieId == INVALID_MOVIE_ID) {
            showError(getString(R.string.error_fetching_movie_trailers));
        }

        return inflatedView;
    }

    private void initialize() {
        Injector.applicationComponent().inject(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        movieReviewsAdapter = new MovieReviewsAdapter(getContext(), this);
        movieReviesListView.setAdapter(movieReviewsAdapter);
        movieReviesListView.setLayoutManager(layoutManager);
        movieReviesListView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.movieReviewsListPresenter.attachTo(this, this.movieId);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LAST_KNOWN_REVIEWS_LIST_POSITION_KEY,
                ((LinearLayoutManager)movieReviesListView.getLayoutManager()).findFirstVisibleItemPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        this.movieReviewsListPresenter.destroy();
        super.onDestroy();
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
        movieReviesListView.setVisibility(GONE);
    }

    private void showContent() {
        hideLoading();
        hideError();
        hideRetry();
        movieReviesListView.setVisibility(VISIBLE);
    }

    @Override
    public void displayMovieReviewsList(List<MovieReviewListItemViewModel> movieReviewsList) {
        this.showContent();
        this.movieReviewsAdapter.setMovieReviewsListItemViewModelsList(movieReviewsList);
        restoreReviewsListPosition();
    }

    private void restoreReviewsListPosition() {
        if(movieReviewsAdapter.getItemCount() < lastKnownReviewsListPosition) {
            movieReviesListView.scrollToPosition(lastKnownReviewsListPosition);
        }
    }

    @Override
    public void expandMovieReview(int index) {

    }

    @Override
    public void onMovieReviewSelected(int reviewIndex) {
        this.movieReviewsListPresenter.movieReviewSelected(reviewIndex);
    }
}
