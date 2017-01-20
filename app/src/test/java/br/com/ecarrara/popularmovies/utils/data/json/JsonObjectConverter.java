package br.com.ecarrara.popularmovies.utils.data.json;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.ecarrara.popularmovies.core.utils.gson.AutoValuesGsonAdapterFactory;
import br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json.Response;

public class JsonObjectConverter {
    private static final String API_DATE_FORMAT = "yyyy-MM-dd";

    private static Gson gson = new GsonBuilder()
            .setDateFormat(API_DATE_FORMAT)
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapterFactory(AutoValuesGsonAdapterFactory.create())
            .create();

    public static <T> T convertFromJson(String json, Class<T> jsonObjectClass) {
        return gson.fromJson(json, jsonObjectClass);
    }
}
