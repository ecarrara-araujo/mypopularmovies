package br.com.ecarrara.popularmovies.movies.presentation.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MovieDetailViewModel {

    public abstract String title();

    public abstract String releaseDate();

    public abstract String posterPath();

    public abstract String backdropPath();

    public abstract Double voteAverage();

    public abstract String plotSynopsis();

    public abstract boolean isFavorite();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_MovieDetailViewModel.Builder()
                .setIsFavorite(false);
    }

    public MovieDetailViewModel withIsFavorite(boolean isFavorite) {
        return toBuilder().setIsFavorite(isFavorite).build();
    }

    @AutoValue.Builder
    abstract static class Builder {

        abstract Builder setTitle(String title);

        abstract Builder setReleaseDate(String releaseDate);

        abstract Builder setPosterPath(String posterPath);

        abstract Builder setBackdropPath(String backdropPath);

        abstract Builder setVoteAverage(Double voteAverage);

        abstract Builder setPlotSynopsis(String plotSynopsis);

        abstract Builder setIsFavorite(boolean isFavorite);

        abstract MovieDetailViewModel build();

    }

}
