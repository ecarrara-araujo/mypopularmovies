package br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue public abstract class MovieDetailResponse {

    @SerializedName("id") public abstract Integer id();
    @SerializedName("original_title") public abstract String originalTitle();
    @SerializedName("overview") public abstract String overview();
    @SerializedName("release_date")public abstract String releaseDate();
    @SerializedName("vote_average") public abstract Double voteAverage();
    @SerializedName("popularity") public abstract Double popularity();
    @SerializedName("poster_path") public abstract String posterPath();

    public static TypeAdapter<MovieDetailResponse> typeAdapter(Gson gson) {
        return new AutoValue_MovieDetailResponse.GsonTypeAdapter(gson);
    }

}
