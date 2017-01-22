package br.com.ecarrara.popularmovies.testdata.movies;

import java.io.IOException;
import java.util.List;

import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json.Response;
import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.mapper.RestMovieMapper;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import br.com.ecarrara.popularmovies.utils.data.json.JsonObjectConverter;
import br.com.ecarrara.popularmovies.utils.data.json.JsonResourceLoader;

public class PopularMoviesTestData {

    public static final String RESPONSE_JSON;
    public static final Response RESPONSE_OBJECT;
    public static final List<Movie> POPULAR_MOVIES_LIST;

    static {
        RESPONSE_JSON = getJson();
        RESPONSE_OBJECT = JsonObjectConverter.convertFromJson(RESPONSE_JSON, Response.class);
        POPULAR_MOVIES_LIST = RestMovieMapper.transformFrom(RESPONSE_OBJECT.results());
    }

    private static String getJson() {
        String json = "";
        try {
            json = JsonResourceLoader
                    .forResource("movies/popular_movies.json")
                    .getJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
