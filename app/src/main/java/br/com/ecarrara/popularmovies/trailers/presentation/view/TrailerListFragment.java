package br.com.ecarrara.popularmovies.trailers.presentation.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModel;
import br.com.ecarrara.popularmovies.trailers.presentation.presenter.TrailerListPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class TrailerListFragment extends Fragment
        implements TrailersListView, TrailersListAdapter.TrailerSelectedListener {

    @BindView(R.id.recycler_view_trailer_list) RecyclerView trailerListView;
    @BindView(R.id.progress_indicator) ProgressBar progressIndicator;
    @BindView(R.id.text_view_error_message) TextView errorDisplay;
    @BindView(R.id.button_retry) ImageButton retryButton;

    private static final String ARGUMENT_MOVIE_ID = "movie_id";
    private static final int INVALID_MOVIE_ID = -1;

    private TrailerListPresenter trailerListPresenter;
    private TrailersListAdapter trailersListAdapter;

    private int movieId = INVALID_MOVIE_ID;

    public TrailerListFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new Trailers List for the informed movieId
     * @param movieId
     * @return
     */
    public static TrailerListFragment newInstance(int movieId) {
        TrailerListFragment trailerListFragment = new TrailerListFragment();
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_MOVIE_ID, movieId);
        trailerListFragment.setArguments(args);
        return trailerListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt(ARGUMENT_MOVIE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.trailer_list_fragment, container, false);
        ButterKnife.bind(this, inflatedView);
        initialize();

        if(movieId == INVALID_MOVIE_ID) {
            showError(getString(R.string.error_fetching_movie_trailers));
        }

        return inflatedView;
    }

    private void initialize() {
        this.trailerListPresenter = new TrailerListPresenter(movieId);
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        trailersListAdapter = new TrailersListAdapter(getContext(), this);
        trailerListView.setAdapter(trailersListAdapter);
        trailerListView.setLayoutManager(layoutManager);
        trailerListView.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.trailerListPresenter.attachTo(this);
    }

    @Override
    public void onDestroy() {
        this.trailerListPresenter.destroy();
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
        trailerListView.setVisibility(GONE);
    }

    private void showContent() {
        hideLoading();
        hideError();
        hideRetry();
        trailerListView.setVisibility(VISIBLE);
    }

    @Override
    public void displayTrailersList(List<TrailerListItemViewModel> trailerListItemViewModelList) {
        this.showContent();
        this.trailersListAdapter.setTrailerListItemViewModelList(trailerListItemViewModelList);
    }

    @Override
    public void playTrailer(String site, String key) {

    }

    @Override
    public void onTrailerSelected(String key, String site) {
        playTrailer(site, key);
    }
}
