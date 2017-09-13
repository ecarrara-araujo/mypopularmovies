package br.com.ecarrara.popularmovies.movies.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Date;

import br.com.ecarrara.popularmovies.core.utils.datetime.DateUtils;

@AutoValue
public abstract class Movie {

    public static final int INVALID_ID = -1;
    public static final String NO_TITLE = "";
    public static final String NO_OVERVIEW = "";
    public static final Date INVALID_DATE = DateUtils.INVALID_DATE;
    public static final String INVALID_FORMATTED_DATE = "";
    public static final double NO_POPULARITY = -1;
    public static final double NO_VOTE_AVERAGE = -1;
    public static final String NO_POSTER_PATH = "";

    public abstract Integer id();

    public abstract String originalTitle();

    public abstract String overview();

    public abstract Date releaseDate();

    public abstract Double popularity();

    public abstract Double voteAverage();

    public abstract String posterPath();

    public abstract String backdropPath();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Movie.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract Builder setId(Integer id);

        public abstract Builder setOriginalTitle(String title);

        public abstract Builder setOverview(String overview);

        public abstract Builder setReleaseDate(Date releaseDate);

        public abstract Builder setPopularity(Double popularity);

        public abstract Builder setVoteAverage(Double voteAverage);

        public abstract Builder setPosterPath(String posterPath);

        public abstract Builder setBackdropPath(String backdropPath);

        public abstract Movie build();

    }

}
