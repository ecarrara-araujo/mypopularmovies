package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.core.di.Injector;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.presenter.MovieDetailPresenter;
import br.com.ecarrara.popularmovies.reviews.presentation.view.MovieReviewsFragment;
import br.com.ecarrara.popularmovies.trailers.presentation.view.TrailerListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailView {

    @Inject MovieDetailPresenter movieDetailPresenter;

    @BindView(R.id.progress_indicator) ProgressBar progressIndicator;
    @BindView(R.id.error_display) ViewGroup errorDisplay;
    @BindView(R.id.text_view_error_message) TextView errorTextDisplay;
    @BindView(R.id.button_retry) ImageButton retryButton;

    @BindView(R.id.movie_detail_content) ViewGroup movieDetailContent;
    @BindView(R.id.movie_poster_image_view) ImageView moviePosterImageView;
    @BindView(R.id.movie_title_text_view) TextView movieTitleTextView;
    @BindView(R.id.movie_release_date_text_view) TextView movieReleaseDateTextView;
    @BindView(R.id.movie_synopsis_text_view) TextView movieSynopsisTextView;
    @BindView(R.id.movie_rating_text_view) TextView movieRatingTextView;

    private int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        initialize();
    }

    private void initialize() {
        Injector.applicationComponent().inject(this);
        processBundle();
        ButterKnife.bind(this);
        setUpActionBar();
        setUpMovieTrailersView();
        setUpMovieReviewsView();
    }

    private void processBundle() {
        final Intent movieDetailIntent = getIntent();
        this.movieId = movieDetailIntent.getIntExtra(MovieDetailView.MOVIE_ID_KEY,
                Movie.INVALID_ID);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpMovieTrailersView() {
        TrailerListFragment trailerListFragment = TrailerListFragment.newInstance(this.movieId);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movie_trailer_container, trailerListFragment)
                .commit();
    }

    private void setUpMovieReviewsView() {
        MovieReviewsFragment movieReviewsFragment = MovieReviewsFragment.newInstance(this.movieId);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movie_reviews_container, movieReviewsFragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.movieDetailPresenter.attachTo(this, this.movieId);
    }

    @Override
    protected void onDestroy() {
        this.movieDetailPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void displayMovieDetail(MovieDetailViewModel movieDetailViewModel) {
        showContent();
        movieTitleTextView.setText(movieDetailViewModel.title());
        movieReleaseDateTextView.setText(movieDetailViewModel.releaseDate());
        movieRatingTextView.setText(getString(R.string.movie_detail_rating_format, movieDetailViewModel.voteAverage()));
        movieSynopsisTextView.setText(movieDetailViewModel.plotSynopsis());

        Picasso.with(MovieDetailActivity.this)
                .load(movieDetailViewModel.posterPath())
                .fit()
                .into(moviePosterImageView);
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
        errorTextDisplay.setText(message);
    }

    @Override
    public void hideError() {
        errorDisplay.setVisibility(GONE);
    }

    private void hideContent() {
        movieDetailContent.setVisibility(GONE);
    }

    private void showContent() {
        hideLoading();
        hideError();
        hideRetry();
        movieDetailContent.setVisibility(VISIBLE);
    }
}
