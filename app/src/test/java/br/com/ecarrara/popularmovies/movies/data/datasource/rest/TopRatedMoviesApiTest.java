package br.com.ecarrara.popularmovies.movies.data.datasource.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import br.com.ecarrara.popularmovies.core.networking.rest.RestApiConnection;
import br.com.ecarrara.popularmovies.movies.data.datasource.rest.json.Response;
import br.com.ecarrara.popularmovies.testdata.movies.TopRatedMoviesTestData;
import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class TopRatedMoviesApiTest {

    private Response expectedResponseObject = TopRatedMoviesTestData.RESPONSE_OBJECT;
    private String responseJson = TopRatedMoviesTestData.RESPONSE_JSON;
    private RestApiConnection restApiConnection;
    private MockWebServer server;

    @Before
    public void setUp() throws IOException {
        server = new MockWebServer();
        server.enqueue(
                new MockResponse()
                        .setBody(this.responseJson)
        );
        server.start();
        restApiConnection = new RestApiConnection(server.url("/").toString());
    }

    @After
    public void tearDown() throws IOException {
        server.shutdown();
    }

    @Test
    public void sucessfulListTopRatedMovies() throws Exception {
        TestObserver<Response> testObserver = new TestObserver<>();

        restApiConnection.connectTo(MoviesRestApi.class)
                .getTopRatedMovies()
                .subscribe(testObserver);

        testObserver.assertResult(this.expectedResponseObject);
    }

}