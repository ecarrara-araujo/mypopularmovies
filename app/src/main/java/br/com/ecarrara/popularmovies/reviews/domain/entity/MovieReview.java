package br.com.ecarrara.popularmovies.reviews.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MovieReview {

    public static final String INVALID_ID = "00000";
    public static final String NO_AUTHOR = "";
    public static final String NO_CONTENT = "";
    public static final String NO_URL = "";

    public abstract String id();
    public abstract String author();
    public abstract String content();
    public abstract String url();

    public static class Builder {

        String id = INVALID_ID;
        String author = NO_AUTHOR;
        String content = NO_CONTENT;
        String url = NO_URL;

        public MovieReview build() {
            return new AutoValue_MovieReview(
                id,
                author,
                content,
                url
            );
        }

        public Builder(String id) {
            this.id = id;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }
    }

}