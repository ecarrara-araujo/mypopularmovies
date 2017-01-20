package br.com.ecarrara.popularmovies.movies.data.repository.datasource.rest.json;


import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

@AutoValue public abstract class Result {

    @SerializedName("poster_path") abstract String posterPath();
    @SerializedName("adult") abstract Boolean adult();
    @SerializedName("overview")abstract String overview();
    @SerializedName("release_date") abstract String releaseDate();
    @SerializedName("genre_ids") abstract List<Integer> genreIds();
    @SerializedName("id") abstract Integer id();
    @SerializedName("original_title") abstract String originalTitle();
    @SerializedName("original_language") abstract String originalLanguage();
    @SerializedName("title") abstract String title();
    @SerializedName("backdrop_path") abstract String backdropPath();
    @SerializedName("popularity") abstract Double popularity();
    @SerializedName("vote_count") abstract Integer voteCount();
    @SerializedName("video") abstract Boolean video();
    @SerializedName("vote_average") abstract Double voteAverage();

    public static TypeAdapter<Result> typeAdapter(Gson gson) {
        return new AutoValue_Result.GsonTypeAdapter(gson)
                .setDefaultGenreIds(Collections.EMPTY_LIST);
    }

}