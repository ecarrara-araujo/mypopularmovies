package br.com.ecarrara.popularmovies.movies.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.favorites.domain.data.FavoritesLocalDataSource;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.data.MoviesRepositoryImpl;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModelMapper;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieListView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesListPresenter implements Presenter<MovieListView, Void> {

    private static final int ACTION_LIST_POPULAR = 0;
    private static final int ACTION_LIST_TOP_RATED = 1;
    private static final int ACTION_LIST_FAVORITES = 2;

    private MoviesRepository moviesRepository;
    private FavoritesLocalDataSource favoritesLocalDataSource;
    private MovieListView movieListView;
    private Disposable moviesListDisposable;

    private int currentAction;

    @Inject
    public MoviesListPresenter(
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
        moviesListDisposable.dispose();
    }

    @Override
    public void attachTo(MovieListView view) {
        this.movieListView = view;
        onListPopularMovies();
    }

    @Override
    public void attachTo(MovieListView view, Void data) {
        attachTo(view);
    }

    public void onRetry() {
        switch (this.currentAction) {
            case ACTION_LIST_POPULAR:
                onListPopularMovies();
                break;
            case ACTION_LIST_TOP_RATED:
                onListTopRatedMovies();
                break;
            default:
                displayError("You should never be here...");
        }
    }

    public void onListPopularMovies() {
        this.currentAction = ACTION_LIST_POPULAR;
        initialize(this.moviesRepository.listPopularMovies());
    }

    public void onListTopRatedMovies() {
        this.currentAction = ACTION_LIST_TOP_RATED;
        initialize(this.moviesRepository.listTopRatedMovies());
    }

    public void onListFavorites() {
        this.currentAction = ACTION_LIST_FAVORITES;
        initialize(favoritesLocalDataSource.list());
    }

    public void onMovieSelected(Integer movieId) {
        this.movieListView.navigateToMovieDetailScreen(movieId);
    }

    private void initialize(Single<List<Movie>> moviesList) {
        this.movieListView.hideRetry();
        this.movieListView.hideError();
        this.movieListView.showLoading();

        moviesListDisposable = moviesList
                .flatMap(movies -> Single.just(MovieListItemViewModelMapper.transformFrom(movies)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayMovieList,
                        exception -> displayError(exception.getMessage())
                );
    }

    private void displayMovieList(List<MovieListItemViewModel> movieListItemModelList) {
        this.movieListView.hideLoading();
        this.movieListView.displayMoviesList(movieListItemModelList);
    }

    private void displayError(String message) {
        this.movieListView.hideLoading();
        this.movieListView.showError(message);
        this.movieListView.showRetry();
    }

}
