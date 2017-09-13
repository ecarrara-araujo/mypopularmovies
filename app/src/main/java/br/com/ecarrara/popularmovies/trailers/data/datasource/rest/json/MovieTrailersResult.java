package br.com.ecarrara.popularmovies.trailers.data.datasource.rest.json;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class MovieTrailersResult {

    @SerializedName("id") public abstract String id();
    @SerializedName("iso_639_1") public abstract String language();
    @SerializedName("iso_3166_1") public abstract String country();
    @SerializedName("key") public abstract String key();
    @SerializedName("name") public abstract String name();
    @SerializedName("site") public abstract String site();
    @SerializedName("size") public abstract Integer size();
    @SerializedName("type") public abstract String type();

    public static TypeAdapter<MovieTrailersResult> typeAdapter(Gson gson) {
        return new AutoValue_MovieTrailersResult.GsonTypeAdapter(gson);
    }
}
