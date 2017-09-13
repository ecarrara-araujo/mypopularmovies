package br.com.ecarrara.popularmovies.movies.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.R;
import br.com.ecarrara.popularmovies.core.di.Injector;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.presenter.MovieDetailPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MovieDetailActivity extends AppCompatActivity implements MovieDetailView {

    @Inject MovieDetailPresenter movieDetailPresenter;

    @BindView(R.id.progress_indicator) ProgressBar progressIndicator;

    @BindView(R.id.error_message_text_view) TextView errorTextDisplay;
    @BindView(R.id.retry_button) Button retryButton;

    @BindView(R.id.movie_detail_backdrop_image_view) ImageView movieBackdropImageView;
    @BindView(R.id.movie_detail_poster_image_view) ImageView moviePosterImageView;
    @BindView(R.id.movie_detail_original_title_text_view) TextView movieTitleTextView;
    @BindView(R.id.movie_detail_release_date_text_view) TextView movieReleaseDateTextView;
    @BindView(R.id.movie_detail_rating_text_view) TextView movieRatingTextView;
    @BindView(R.id.movie_detail_add_to_favorites_checkbox) CheckBox movieAddToFavoriteCheckBox;
    @BindView(R.id.movie_details_more_info_view_pager) ViewPager movieAdditionalInfoViewPager;
    @BindView(R.id.movie_details_more_info_tabs) TabLayout movieAdditionalInfoTabs;

    private int movieId;
    private MovieMoreInfoViewPagerAdapter movieMoreInfoViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);
        initialize();
    }

    private void initialize() {
        Injector.applicationComponent().inject(this);
        processIntentParameters();
        ButterKnife.bind(this);
        setUpActionBar();
        setUpMovieInfoViewPager();
    }

    private void processIntentParameters() {
        final Intent movieDetailIntent = getIntent();
        this.movieId = movieDetailIntent.getIntExtra(MovieDetailView.MOVIE_ID_KEY,
                Movie.INVALID_ID);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.movie_detail);
        }
    }

    private void setUpMovieInfoViewPager() {
        movieMoreInfoViewPagerAdapter = new MovieMoreInfoViewPagerAdapter(
                getSupportFragmentManager(), getApplicationContext());
        this.movieAdditionalInfoViewPager.setAdapter(movieMoreInfoViewPagerAdapter);
        this.movieAdditionalInfoTabs.setupWithViewPager(this.movieAdditionalInfoViewPager);
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
        movieMoreInfoViewPagerAdapter.setMovieData(movieId, movieDetailViewModel.plotSynopsis());
        showContent();
        movieTitleTextView.setText(movieDetailViewModel.title());
        movieReleaseDateTextView.setText(movieDetailViewModel.releaseDate());
        movieRatingTextView.setText(getString(R.string.movie_detail_rating_format, movieDetailViewModel.voteAverage()));
        movieAddToFavoriteCheckBox.setChecked(movieDetailViewModel.isFavorite());

        Picasso.with(MovieDetailActivity.this)
                .load(movieDetailViewModel.posterPath())
                .fit()
                .into(moviePosterImageView);

        Picasso.with(MovieDetailActivity.this)
                .load(movieDetailViewModel.backdropPath())
                .fit()
                .into(movieBackdropImageView);
    }


    @Override
    public void setAddToFavoritesStateTo(boolean isOnFavorites) {
        movieAddToFavoriteCheckBox.setChecked(isOnFavorites);
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
        showRetry();
        errorTextDisplay.setVisibility(VISIBLE);
        errorTextDisplay.setText(message);
    }

    @Override
    public void hideError() {
        hideRetry();
        errorTextDisplay.setVisibility(GONE);
    }

    private void hideContent() {
        movieBackdropImageView.setVisibility(GONE);
        moviePosterImageView.setVisibility(GONE);
        movieTitleTextView.setVisibility(GONE);
        movieReleaseDateTextView.setVisibility(GONE);
        movieRatingTextView.setVisibility(GONE);
        movieAddToFavoriteCheckBox.setVisibility(GONE);
        movieAdditionalInfoViewPager.setVisibility(GONE);
    }

    private void showContent() {
        hideLoading();
        hideError();
        hideRetry();
        movieBackdropImageView.setVisibility(VISIBLE);
        moviePosterImageView.setVisibility(VISIBLE);
        movieTitleTextView.setVisibility(VISIBLE);
        movieReleaseDateTextView.setVisibility(VISIBLE);
        movieRatingTextView.setVisibility(VISIBLE);
        movieAddToFavoriteCheckBox.setVisibility(VISIBLE);
        movieAdditionalInfoViewPager.setVisibility(VISIBLE);
    }

    @OnClick(R.id.movie_detail_add_to_favorites_checkbox)
    public void isFavoriteChanged(View view) {
        movieDetailPresenter.favoriteStateChanged(movieAddToFavoriteCheckBox.isChecked());
    }

}
