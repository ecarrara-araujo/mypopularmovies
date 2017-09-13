package br.com.ecarrara.popularmovies.movies.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.core.networking.connectivity.ConnectivityObserver;
import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.favorites.domain.data.FavoritesLocalDataSource;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModelMapper;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieListView;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesListPresenter implements Presenter<MovieListView, Void> {

    private static final int ACTION_LIST_POPULAR = 0;
    private static final int ACTION_LIST_TOP_RATED = 1;
    private static final int ACTION_LIST_FAVORITES = 2;

    private MoviesRepository moviesRepository;
    private FavoritesLocalDataSource favoritesLocalDataSource;
    private ConnectivityObserver connectivityObserver;
    private MovieListView movieListView;
    private CompositeDisposable disposables = new CompositeDisposable();

    private int currentAction;
    private Boolean isConnected = false;

    @Inject
    public MoviesListPresenter(
            MoviesRepository moviesRepository,
            FavoritesLocalDataSource favoritesLocalDataSource,
            ConnectivityObserver connectivityObserver) {
        this.moviesRepository = moviesRepository;
        this.favoritesLocalDataSource = favoritesLocalDataSource;
        this.connectivityObserver = connectivityObserver;
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
    public void attachTo(MovieListView view) {
        this.movieListView = view;
        onAttachLoadMoviesList();
    }

    private void onAttachLoadMoviesList() {
        initialize(
                this.connectivityObserver.observeConnectivity()
                        .firstOrError()
                        .doOnSuccess(isConnected -> MoviesListPresenter.this.isConnected = isConnected)
                        .flatMap(isConnected -> {
                            if (isConnected) {
                                this.currentAction = ACTION_LIST_POPULAR;
                                return moviesRepository.listPopularMovies();
                            } else {
                                this.currentAction = ACTION_LIST_FAVORITES;
                                return favoritesLocalDataSource.list();
                            }
                        })
                        .doAfterSuccess(movies -> observeConnectivityChanges())
        );
    }

    private void observeConnectivityChanges() {
        disposables.add(this.connectivityObserver.observeConnectivity()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(isConnected -> MoviesListPresenter.this.isConnected = isConnected)
                .subscribe(this::connectivityStateChanged)
        );
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
            case ACTION_LIST_FAVORITES:
                onListFavorites();
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

        disposables.add(moviesList
                .flatMap(movies -> Single.just(MovieListItemViewModelMapper.transformFrom(movies)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayMovieList,
                        exception -> displayError(exception.getMessage())
                )
        );
    }

    private void displayMovieList(List<MovieListItemViewModel> movieListItemModelList) {
        this.movieListView.hideLoading();
        if (isConnected) {
            movieListView.setUpToConnectivityOnline();
        } else {
            movieListView.setUpToConnectivityOffline();
        }
        this.movieListView.displayMoviesList(movieListItemModelList);
    }

    private void displayError(String message) {
        this.movieListView.hideLoading();
        this.movieListView.showError(message);
        this.movieListView.showRetry();
    }

    private void connectivityStateChanged(Boolean isConnected) {
        if (isConnected) {
            this.movieListView.setUpToConnectivityOnline();
        } else {
            this.movieListView.setUpToConnectivityOffline();
            this.onListFavorites();
        }
    }
}
