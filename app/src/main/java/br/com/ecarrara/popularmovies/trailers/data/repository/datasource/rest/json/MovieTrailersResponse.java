package br.com.ecarrara.popularmovies.trailers.data.repository.datasource.rest.json;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@AutoValue public abstract class MovieTrailersResponse {

    @SerializedName("id") public abstract Integer id();
    @SerializedName("results") public abstract List<MovieTrailersResult> results();

    public static TypeAdapter<MovieTrailersResponse> typeAdapter(Gson gson) {
        return new AutoValue_MovieTrailersResponse.GsonTypeAdapter(gson)
                .setDefaultResults(Collections.EMPTY_LIST);
    }
}
