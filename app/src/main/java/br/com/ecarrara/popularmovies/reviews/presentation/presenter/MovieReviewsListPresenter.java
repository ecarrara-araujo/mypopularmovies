package br.com.ecarrara.popularmovies.reviews.presentation.presenter;

import java.util.List;

import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.reviews.domain.MovieReviewsRepository;
import br.com.ecarrara.popularmovies.reviews.data.MovieReviewsRepositoryImpl;
import br.com.ecarrara.popularmovies.reviews.presentation.model.MovieReviewListItemViewModel;
import br.com.ecarrara.popularmovies.reviews.presentation.model.MovieReviewListItemViewModelMapper;
import br.com.ecarrara.popularmovies.reviews.presentation.view.MovieReviewsListView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieReviewsListPresenter implements Presenter<MovieReviewsListView> {

    private MovieReviewsRepository movieReviewsRepository;
    private Disposable movieReviewsListDisposable;
    private MovieReviewsListView movieReviewsListView;

    private int movieId;

    public MovieReviewsListPresenter(int movieId) {
        this(movieId, new MovieReviewsRepositoryImpl());
    }

    public MovieReviewsListPresenter(int movieId, MovieReviewsRepository movieReviewsRepository) {
        this.movieReviewsRepository = movieReviewsRepository;
        this.movieId = movieId;
    }

    @Override
    public void resume() { /* no operation */ }

    @Override
    public void pause() { /* no operation */ }

    @Override
    public void destroy() {
        this.movieReviewsListDisposable.dispose();
    }

    @Override
    public void attachTo(MovieReviewsListView movieReviewsListView) {
        this.movieReviewsListView = movieReviewsListView;
        displayMovieReviews();
    }

    private void displayMovieReviews() {
        this.movieReviewsListView.hideRetry();
        this.movieReviewsListView.hideError();
        this.movieReviewsListView.showLoading();

        this.movieReviewsListDisposable = this.movieReviewsRepository
                .listMovieReviews(movieId)
                .map(MovieReviewListItemViewModelMapper::transformFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    this::displayMovieReviewsList,
                    exception -> displayError(exception.getMessage())
                );
    }

    private void displayMovieReviewsList(List<MovieReviewListItemViewModel> movieReviewListItemViewModels) {
        this.movieReviewsListView.hideLoading();
        this.movieReviewsListView.displayMovieReviewsList(movieReviewListItemViewModels);
    }

    private void displayError(String message) {
        this.movieReviewsListView.hideLoading();
        this.movieReviewsListView.showError(message);
    }

    public void movieReviewSelected(int index) {
        this.movieReviewsListView.expandMovieReview(index);
    }
}
