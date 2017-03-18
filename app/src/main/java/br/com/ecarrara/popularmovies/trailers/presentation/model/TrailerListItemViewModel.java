package br.com.ecarrara.popularmovies.trailers.presentation.model;

import com.google.auto.value.AutoValue;

@AutoValue public abstract class TrailerListItemViewModel {

    public abstract String key();
    public abstract String name();
    public abstract String site();

    public static TrailerListItemViewModel create(String key, String name, String site) {
        return new AutoValue_TrailerListItemViewModel(key, name, site);
    }

}
