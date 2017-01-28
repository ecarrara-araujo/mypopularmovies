package br.com.ecarrara.popularmovies.movies.presentation.model;

import com.google.auto.value.AutoValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.ecarrara.popularmovies.core.networking.rest.RestImageUrlBuilder;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;

@AutoValue public abstract class MovieDetailViewModel {

    public abstract String title();
    public abstract String releaseDate();
    public abstract String posterPath();
    public abstract Double voteAverage();
    public abstract String plotSynopsis();

    public static class Builder {
        private String title = Movie.NO_TITLE;
        private String releaseDate = Movie.INVALID_FORMATTED_DATE;
        private String plotSynopsis = Movie.NO_OVERVIEW;
        private Double voteAverage = Movie.NO_VOTE_AVERAGE;
        private String posterPath = Movie.NO_POSTER_PATH;

        public MovieDetailViewModel build() {
            return new AutoValue_MovieDetailViewModel(
                this.title,
                this.releaseDate,
                this.posterPath,
                this.voteAverage,
                this.plotSynopsis
            );
        }

        public Builder(String title) {
            this.title = title;
        }

        public Builder setDate(Date releaseDate) {
            DateFormat dateFormat = SimpleDateFormat.getDateInstance();
            this.releaseDate = dateFormat.format(releaseDate);
            return this;
        }

        public Builder setPosterPath(String posterPath) {
            this.posterPath = RestImageUrlBuilder.build(posterPath);
            return this;
        }

        public Builder setVoteAverage(Double voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }

        public Builder setPlotSynopsis(String plotSynopsis) {
            this.plotSynopsis = plotSynopsis;
            return this;
        }
    }

}
