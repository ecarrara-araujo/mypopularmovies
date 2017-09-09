package br.com.ecarrara.popularmovies.movies.presentation.presenter;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.favorites.domain.data.FavoritesLocalDataSource;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.data.MoviesRepositoryImpl;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieDetailViewModelMapper;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieDetailView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailPresenter implements Presenter<MovieDetailView, Integer> {

    private MoviesRepository moviesRepository;
    private FavoritesLocalDataSource favoritesLocalDataSource;
    private CompositeDisposable disposables = new CompositeDisposable();

    private int movieId;
    private Movie movie;
    private MovieDetailView movieDetailView;

    @Inject
    public MovieDetailPresenter(
            MoviesRepository moviesRepository,
            FavoritesLocalDataSource favoritesLocalDataSource) {
        this.moviesRepository = moviesRepository;
        this.favoritesLocalDataSource = favoritesLocalDataSource;
    }

    @Override
    public void resume() { /* no operation */ }

    @Override
    public void pause() { /* no operation */ }

    @Override
    public void destroy() {
        disposables.dispose();
    }

    @Override
    public void attachTo(MovieDetailView view) {
        attachTo(view, Movie.INVALID_ID);
    }

    @Override
    public void attachTo(MovieDetailView view, Integer data) {
        this.movieDetailView = view;
        this.movieId = data;
        displayMovieDetail();
    }

    private void displayMovieDetail() {
        this.movieDetailView.hideRetry();
        this.movieDetailView.hideError();
        this.movieDetailView.showLoading();

        disposables.add(moviesRepository.getMovieDetail(this.movieId)
                .doOnSuccess(movie -> this.movie = movie)
                .flatMap(movie ->
                        favoritesLocalDataSource.isFavorite(movie)
                                .flatMap(isFavorite ->
                                        Single.just(MovieDetailViewModelMapper
                                                .transformFrom(movie, isFavorite))
                                ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayMovieDetail,
                        exception -> displayError(exception.getMessage())
                )
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

    public void favoriteStateChanged(boolean isFavorite) {
        if(isFavorite) {
            addMovieToFavorites();
        } else {
            removeMovieFromFavorites();
        }
    }

    private void addMovieToFavorites() {
        disposables.add(
            favoritesLocalDataSource.save(movie)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> { /* no op */ },
                            exception -> {
                                displayError(exception.getMessage());
                                movieDetailView.setAddToFavoritesStateTo(false);
                            }
                    )
        );
    }

    private void removeMovieFromFavorites() {
        disposables.add(
                favoritesLocalDataSource.delete(movie)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> { /* no op */ },
                                exception -> {
                                    displayError(exception.getMessage());
                                    movieDetailView.setAddToFavoritesStateTo(true);
                                }
                        )
        );
    }

}
