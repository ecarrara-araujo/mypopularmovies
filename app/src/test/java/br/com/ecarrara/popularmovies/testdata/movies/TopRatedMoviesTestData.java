package br.com.ecarrara.popularmovies.testdata.movies;

import java.io.IOException;

import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json.Response;
import br.com.ecarrara.popularmovies.utils.data.json.JsonObjectConverter;
import br.com.ecarrara.popularmovies.utils.data.json.JsonResourceLoader;

public class TopRatedMoviesTestData {

    public static final String RESPONSE_JSON;
    public static final Response RESPONSE_OBJECT;

    static {
        RESPONSE_JSON = getJson();
        RESPONSE_OBJECT = JsonObjectConverter.convertFromJson(RESPONSE_JSON, Response.class);
    }

    private static String getJson() {
        String json = "";
        try {
            json = JsonResourceLoader
                    .forResource("movies/top_rated_movies.json")
                    .getJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

}
