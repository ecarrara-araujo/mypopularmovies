package br.com.ecarrara.popularmovies.reviews.data.repository.datasource.rest.mapper;

import java.util.ArrayList;
import java.util.List;

import br.com.ecarrara.popularmovies.reviews.data.repository.datasource.rest.json.MovieReviewResult;
import br.com.ecarrara.popularmovies.reviews.domain.entity.MovieReview;

public class RestReviewMapper {

    public static MovieReview transformFrom(MovieReviewResult movieReviewResult) {
        return new MovieReview.Builder(movieReviewResult.id())
                .setAuthor(movieReviewResult.author())
                .setContent(movieReviewResult.content())
                .setUrl(movieReviewResult.url())
                .build();
    }

    public static List<MovieReview> transformFrom(List<MovieReviewResult> movieReviewResults) {
        List<MovieReview> transformedMovieReviews = new ArrayList<>(movieReviewResults.size());
        for (MovieReviewResult movieReviewResult : movieReviewResults) {
            transformedMovieReviews.add(transformFrom(movieReviewResult));
        }
        return transformedMovieReviews;
    }

}
