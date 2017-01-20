package br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@AutoValue public abstract class Response {

    @SerializedName("page") abstract Integer page();
    @SerializedName("results") abstract List<Result> results();
    @SerializedName("total_results") abstract Integer totalResults();
    @SerializedName("total_pages") abstract Integer totalPages();

    public static TypeAdapter<Response> typeAdapter(Gson gson) {
        return new AutoValue_Response.GsonTypeAdapter(gson)
                .setDefaultResults(Collections.EMPTY_LIST);
    }
}
