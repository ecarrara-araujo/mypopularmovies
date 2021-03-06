package br.com.ecarrara.popularmovies.movies.data.datasource.rest.json;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@AutoValue public abstract class Response {

    @SerializedName("page") public abstract Integer page();
    @SerializedName("results") public abstract List<Result> results();
    @SerializedName("total_results") public abstract Integer totalResults();
    @SerializedName("total_pages")public abstract Integer totalPages();

    public static TypeAdapter<Response> typeAdapter(Gson gson) {
        return new AutoValue_Response.GsonTypeAdapter(gson)
                .setDefaultResults(Collections.EMPTY_LIST);
    }
}
