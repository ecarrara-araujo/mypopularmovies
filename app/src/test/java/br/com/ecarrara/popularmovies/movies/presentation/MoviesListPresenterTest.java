package br.com.ecarrara.popularmovies.movies.presentation;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import br.com.ecarrara.popularmovies.movies.data.repository.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModel;
import br.com.ecarrara.popularmovies.movies.presentation.model.MovieListItemViewModelMapper;
import br.com.ecarrara.popularmovies.movies.presentation.presenter.MoviesListPresenter;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieListView;
import br.com.ecarrara.popularmovies.testdata.movies.PopularMoviesTestData;
import br.com.ecarrara.popularmovies.testdata.movies.TopRatedMoviesTestData;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MoviesListPresenterTest {

    private MoviesListPresenter presenter;

    private final List<Movie> popularMovieList = PopularMoviesTestData.POPULAR_MOVIES_LIST;
    private final List<Movie> topRatedMovieList = TopRatedMoviesTestData.TOP_RATED_MOVIES_LIST;

    private final List<MovieListItemViewModel> expectedPopularMoviesListItemModelViewList
            = MovieListItemViewModelMapper.transformFrom(popularMovieList);

    private final List<MovieListItemViewModel> expectedTopRatedMoviesListItemModelViewList
            = MovieListItemViewModelMapper.transformFrom(topRatedMovieList);

    @Mock
    MovieListView mockView;

    @Mock
    MoviesRepository moviesRepository;

    @BeforeClass
    public static void setupJVMRxHandler() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                __ -> Schedulers.trampoline()
        );
    }

    @Before
    public void setUp() {
        presenter = new MoviesListPresenter(moviesRepository);
    }

    @Test
    public void successfulPresenterInitialization() {
        when(moviesRepository.listPopularMovies())
                .thenReturn(Single.just(popularMovieList));

        presenter.attachTo(mockView);

        inOrder(mockView);
        verifyViewLoading();
        verifyViewLoaded(expectedPopularMoviesListItemModelViewList);
    }

    private void verifyViewLoading() {
        verify(mockView).hideRetry();
        verify(mockView).hideError();
        verify(mockView).showLoading();
    }

    private void verifyViewLoaded(List<MovieListItemViewModel> moviesListItems) {
        verify(mockView).hideLoading();
        verify(mockView).displayMoviesList(moviesListItems);
    }

}