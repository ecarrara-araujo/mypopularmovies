package br.com.ecarrara.popularmovies.movies.presentation.presenter;

import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.data.MoviesRepositoryImpl;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModelMapper;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieDetailView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter implements Presenter<MovieDetailView> {

    private MoviesRepository moviesRepository;
    private Disposable movieDetailDisposable;

    private int movieId;
    private MovieDetailView movieDetailView;

    public MovieDetailPresenter(int movieId) {
        this(movieId, new MoviesRepositoryImpl());
    }

    public MovieDetailPresenter(int movieId, MoviesRepository moviesRepository) {
        this.movieId = movieId;
        this.moviesRepository = moviesRepository;
    }

    @Override
    public void resume() { /* no operation */ }

    @Override
    public void pause() { /* no operation */ }

    @Override
    public void destroy() {
        movieDetailDisposable.dispose();
    }

    @Override
    public void attachTo(MovieDetailView view) {
        this.movieDetailView = view;
        displayMovieDetail();
    }

    private void displayMovieDetail() {
        this.movieDetailView.hideRetry();
        this.movieDetailView.hideError();
        this.movieDetailView.showLoading();

        movieDetailDisposable = moviesRepository.getMovieDetail(this.movieId)
                .map(MovieDetailViewModelMapper::transformFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayMovieDetail,
                        exception -> displayError(exception.getMessage())
                );
    }

    private void displayMovieDetail(MovieDetailViewModel movieDetailViewModel) {
        this.movieDetailView.hideLoading();
        this.movieDetailView.displayMovieDetail(movieDetailViewModel);
    }

    private void displayError(String message) {
        this.movieDetailView.hideLoading();
        this.movieDetailView.showError(message);
        this.movieDetailView.showRetry();
    }
}
