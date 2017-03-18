package br.com.ecarrara.popularmovies.reviews.data.repository.datasource.rest.json;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class MovieReviewResult {

    @SerializedName("id") public abstract String id();
    @SerializedName("author") public abstract String author();
    @SerializedName("content") public abstract String content();
    @SerializedName("url") public abstract String url();

    public static TypeAdapter<MovieReviewResult> typeAdapter(Gson gson) {
        return new AutoValue_MovieReviewResult.GsonTypeAdapter(gson);
    }

}
