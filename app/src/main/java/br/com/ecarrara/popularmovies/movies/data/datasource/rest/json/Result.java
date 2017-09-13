package br.com.ecarrara.popularmovies.movies.data.datasource.rest.json;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@AutoValue public abstract class Result {

    @SerializedName("poster_path") public abstract String posterPath();
    @SerializedName("adult") public abstract Boolean adult();
    @SerializedName("overview") public abstract String overview();
    @SerializedName("release_date") public abstract String releaseDate();
    @SerializedName("genre_ids") public abstract List<Integer> genreIds();
    @SerializedName("id") public abstract Integer id();
    @SerializedName("original_title") public abstract String originalTitle();
    @SerializedName("original_language") public abstract String originalLanguage();
    @SerializedName("title") public abstract String title();
    @SerializedName("backdrop_path") public abstract String backdropPath();
    @SerializedName("popularity") public abstract Double popularity();
    @SerializedName("vote_count") public abstract Integer voteCount();
    @SerializedName("video") public abstract Boolean video();
    @SerializedName("vote_average") public abstract Double voteAverage();

    public static TypeAdapter<Result> typeAdapter(Gson gson) {
        return new AutoValue_Result.GsonTypeAdapter(gson)
                .setDefaultGenreIds(Collections.EMPTY_LIST);
    }

}