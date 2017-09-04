package br.com.ecarrara.popularmovies.movies.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import br.com.ecarrara.popularmovies.movies.data.MoviesRepositoryImpl;
import br.com.ecarrara.popularmovies.movies.data.datasource.MoviesDataSource;
import br.com.ecarrara.popularmovies.movies.domain.MoviesRepository;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.testdata.movies.TopRatedMoviesTestData;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TopRatedMoviesRepositoryTest {

    private final List<Movie> expectedMovieList = TopRatedMoviesTestData.TOP_RATED_MOVIES_LIST;

    @Mock
    private MoviesDataSource mockMoviesDataSource;

    private MoviesRepository moviesRepository;

    @Before
    public void setUp() throws IOException {
        moviesRepository = new MoviesRepositoryImpl(mockMoviesDataSource);
    }

    @Test
    public void successfulListPopularMovies() throws Exception {
        when(mockMoviesDataSource.listTopRatedMovies()).thenReturn(Single.just(expectedMovieList));
        TestObserver<List<Movie>> testObserver = new TestObserver<>();

        moviesRepository
                .listTopRatedMovies()
                .subscribe(testObserver);

        testObserver.assertResult(expectedMovieList);
    }


}