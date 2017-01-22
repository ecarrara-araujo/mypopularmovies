package br.com.ecarrara.popularmovies.movies.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Calendar;
import java.util.Date;

import br.com.ecarrara.popularmovies.core.utils.datetime.DateUtils;

@AutoValue public abstract class Movie {

    public static final int INVALID_ID = -1;
    public static final String NO_TITLE = "";
    public static final String NO_OVERVIEW = "";
    public static final Date INVALID_DATE = DateUtils.INVALID_DATE;
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

    public static class Builder {
        private Integer id = INVALID_ID;
        private String originalTitle = NO_TITLE;
        private String overview = NO_OVERVIEW;
        private Date releaseDate = INVALID_DATE;
        private Double popularity = NO_POPULARITY;
        private Double voteAverage = NO_VOTE_AVERAGE;
        private String posterPath = NO_POSTER_PATH;

        public Movie build() {
            return new AutoValue_Movie(
                    this.id,
                    this.originalTitle,
                    this.overview,
                    this.releaseDate,
                    this.popularity,
                    this.voteAverage,
                    this.posterPath
            );
        }

        public Builder(int id, String originalTitle) {
            this.id = id;
            this.originalTitle = originalTitle;
        }

        public Builder setOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setPopularity(Double popularity) {
            this.popularity = popularity;
            return this;
        }

        public Builder setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder setPosterPath(String posterPath) {
            this.posterPath = posterPath;
            return this;
        }
    }

}
