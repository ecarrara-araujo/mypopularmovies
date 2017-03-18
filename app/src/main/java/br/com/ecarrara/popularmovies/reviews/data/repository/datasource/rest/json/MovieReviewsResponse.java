package br.com.ecarrara.popularmovies.reviews.data.repository.datasource.rest.json;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@AutoValue public abstract class MovieReviewsResponse {

    @SerializedName("id") public abstract Integer id();
    @SerializedName("page") public abstract Integer page();
    @SerializedName("total_pages") public abstract Integer total_pages();
    @SerializedName("total_results") public abstract Integer total_results();
    @SerializedName("results") public abstract List<MovieReviewResult> results();

    public static TypeAdapter<MovieReviewsResponse> typeAdapter(Gson gson) {
        return new AutoValue_MovieReviewsResponse.GsonTypeAdapter(gson)
                .setDefaultResults(Collections.EMPTY_LIST);
    }

}
