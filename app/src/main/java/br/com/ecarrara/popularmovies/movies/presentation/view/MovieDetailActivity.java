package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.movies.presentation.presenter.MovieDetailPresenter;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailView {

    private int movieId;
    private MovieDetailPresenter movieDetailPresenter;

    private ProgressBar progressIndicator;
    private ViewGroup errorDisplay;
    private TextView errorTextDisplay;
    private ImageButton retryButton;

    private ViewGroup movieDetailContent;
    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView movieReleaseDateTextView;
    private TextView movieSynopsisTextView;
    private RatingBar movieRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        initialize();
    }

    private void initialize() {
        processBundle();
        this.movieDetailPresenter = new MovieDetailPresenter(this.movieId);

        progressIndicator = (ProgressBar) findViewById(R.id.progress_indicator);
        errorDisplay = (ViewGroup) findViewById(R.id.error_display);
        errorTextDisplay = (TextView) findViewById(R.id.text_view_error_message);
        retryButton = (ImageButton) findViewById(R.id.button_retry);

        movieDetailContent = (ViewGroup) findViewById(R.id.movie_detail_content);
        moviePosterImageView = (ImageView) findViewById(R.id.movie_poster_image_view);
        movieTitleTextView = (TextView) findViewById(R.id.movie_title_text_view);
        movieReleaseDateTextView = (TextView) findViewById(R.id.movie_release_date_text_view);
        movieRatingBar = (RatingBar) findViewById(R.id.movie_rating_bar);
        movieSynopsisTextView = (TextView) findViewById(R.id.movie_synopsis_text_view);
    }

    private void processBundle() {
        final Intent movieDetailIntent = getIntent();
        this.movieId = movieDetailIntent.getIntExtra(MovieDetailView.MOVIE_ID_KEY,
                MovieDetailView.NO_MOVIE_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.movieDetailPresenter.attachTo(this);
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
        movieRatingBar.setRating(movieDetailViewModel.voteAverage().floatValue());
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
