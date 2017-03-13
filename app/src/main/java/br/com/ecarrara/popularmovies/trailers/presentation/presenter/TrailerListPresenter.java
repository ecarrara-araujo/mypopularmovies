package br.com.ecarrara.popularmovies.trailers.presentation.presenter;

import java.util.List;

import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.trailers.data.repository.TrailersRepository;
import br.com.ecarrara.popularmovies.trailers.data.repository.TrailersRepositoryImpl;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModel;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModelMapper;
import br.com.ecarrara.popularmovies.trailers.presentation.view.TrailersListView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class TrailerListPresenter implements Presenter<TrailersListView> {

    private TrailersRepository trailersRepository;
    private TrailersListView trailersListView;
    private Disposable trailersListDisposable = Disposables.empty();

    private int movieId;

    public TrailerListPresenter(int movieId) {
        this(movieId, new TrailersRepositoryImpl());
    }

    public TrailerListPresenter(int movieId, TrailersRepository trailersRepository) {
        this.movieId = movieId;
        this.trailersRepository = trailersRepository;
    }

    @Override
    public void resume() { /* no operation */ }

    @Override
    public void pause() { /* no operation */ }

    @Override
    public void destroy() {
        this.trailersListDisposable.dispose();
    }

    @Override
    public void attachTo(TrailersListView view) {
        this.trailersListView = view;
        displayMovieTrailers();
    }

    private void displayMovieTrailers() {
        this.trailersListView.hideRetry();
        this.trailersListView.hideError();
        this.trailersListView.showLoading();

        trailersListDisposable = trailersRepository.listMovieTrailers(movieId)
                .map(TrailerListItemViewModelMapper::transformFrom)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::displayTrailersList,
                        exception -> displayError(exception.getMessage())
                );
    }

    private void displayTrailersList(List<TrailerListItemViewModel> trailerListItemViewModelList) {
        this.trailersListView.hideLoading();
        this.trailersListView.displayTrailersList(trailerListItemViewModelList);
    }

    private void displayError(String message) {
        this.trailersListView.hideLoading();
        this.trailersListView.showError(message);
    }

}
