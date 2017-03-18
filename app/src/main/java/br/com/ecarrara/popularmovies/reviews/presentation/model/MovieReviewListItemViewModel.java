package br.com.ecarrara.popularmovies.reviews.presentation.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class MovieReviewListItemViewModel {

    public abstract String author();
    public abstract String content();

    public static MovieReviewListItemViewModel create(String author, String content) {
        return new AutoValue_MovieReviewListItemViewModel(author, content);
    }

}
