package br.com.ecarrara.popularmovies.trailers.presentation.presenter;

import java.util.List;

import javax.inject.Inject;

import br.com.ecarrara.popularmovies.core.presentation.Presenter;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.trailers.domain.TrailersRepository;
import br.com.ecarrara.popularmovies.trailers.data.TrailersRepositoryImpl;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModel;
import br.com.ecarrara.popularmovies.trailers.presentation.model.TrailerListItemViewModelMapper;
import br.com.ecarrara.popularmovies.trailers.presentation.view.TrailersListView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.schedulers.Schedulers;

public class TrailerListPresenter implements Presenter<TrailersListView, Integer> {

    private TrailersRepository trailersRepository;
    private TrailersListView trailersListView;
    private Disposable trailersListDisposable = Disposables.empty();

    private int movieId;

    @Inject
    public TrailerListPresenter(TrailersRepository trailersRepository) {
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
        attachTo(view, Movie.INVALID_ID);
    }

    @Override
    public void attachTo(TrailersListView view, Integer data) {
        this.trailersListView = view;
        this.movieId = data;
        displayMovieTrailers();
    }

    public void trailerSelected(String site, String key) {
        this.trailersListView.playTrailer(site, key);
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
