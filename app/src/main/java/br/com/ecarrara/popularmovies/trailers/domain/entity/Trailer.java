package br.com.ecarrara.popularmovies.trailers.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Trailer {

    public static final String INVALID_ID = "00000";
    public static final String NO_LANGUAGE = "";
    public static final String NO_COUNTRY = "";
    public static final String NO_KEY = "";
    public static final String NO_NAME = "";
    public static final String NO_SITE = "";
    public static final Integer INVALID_SIZE = -1;
    public static final String NO_TYPE = "";

    public abstract String id();

    public abstract String language();

    public abstract String country();

    public abstract String key();

    public abstract String name();

    public abstract String site();

    public abstract Integer size();

    public abstract String type();

    public static class Builder {
        String id = INVALID_ID;
        String language = NO_LANGUAGE;
        String country = NO_COUNTRY;
        String key = NO_KEY;
        String name = NO_NAME;
        String site = NO_SITE;
        Integer size = INVALID_SIZE;
        String type = NO_TYPE;


        public Trailer build() {
            return new AutoValue_Trailer(
                id,
                language,
                country,
                key,
                name,
                site,
                size,
                type
            );
        }

        public Builder(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder setLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setSite(String site) {
            this.site = site;
            return this;
        }

        public Builder setSize(Integer size) {
            this.size = size;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }
    }

}
