package br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.testdata.movies.PopularMoviesTestData;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class PopularMoviesRestApiDataSourceTest {

    private final List<Movie> expectedMovieList = PopularMoviesTestData.POPULAR_MOVIES_LIST;
    private final String responseJson = PopularMoviesTestData.RESPONSE_JSON;
    private MockWebServer server;
    private RestApiConnection restApiConnection;
    private MoviesRestApiDataSource moviesRestApiDataSource;

    @Before
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(
                new MockResponse()
                        .setBody(this.responseJson)
        );
        server.start();
        restApiConnection = new RestApiConnection(server.url("/").toString());
        moviesRestApiDataSource = new MoviesRestApiDataSource(restApiConnection);
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void successfulListPopularMovies() throws Exception {
        TestObserver<List<Movie>> testObserver = new TestObserver<>();

        moviesRestApiDataSource
                .listPopularMovies()
                .subscribe(testObserver);

        testObserver.assertResult(expectedMovieList);
    }

}