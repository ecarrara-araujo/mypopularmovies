package br.com.ecarrara.popularmovies.movies.presentation.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MovieDetailViewModel {

    public abstract String title();

    public abstract String releaseDate();

    public abstract String posterPath();

    public abstract Double voteAverage();

    public abstract String plotSynopsis();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_MovieDetailViewModel.Builder();
    }

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setTitle(String title);

        abstract Builder setReleaseDate(String releaseDate);

        abstract Builder setPosterPath(String posterPath);

        abstract Builder setVoteAverage(Double voteAverage);

        abstract Builder setPlotSynopsis(String plotSynopsis);

        abstract MovieDetailViewModel build();
    }

}
