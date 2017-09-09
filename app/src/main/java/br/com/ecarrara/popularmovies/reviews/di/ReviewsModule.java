package br.com.ecarrara.popularmovies.reviews.di;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.reviews.data.MovieReviewsRepositoryImpl;
import br.com.ecarrara.popularmovies.reviews.data.datasource.MovieReviewsDataSource;
import br.com.ecarrara.popularmovies.reviews.data.datasource.rest.MovieReviewsRestApiDataSource;
import br.com.ecarrara.popularmovies.reviews.domain.MovieReviewsRepository;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ReviewsModule {

    @Provides
    @Singleton
    public MovieReviewsRepository providesMovieReviewsRepository(MovieReviewsDataSource movieReviewsDataSource) {
        return new MovieReviewsRepositoryImpl(movieReviewsDataSource);
    }

    @Provides
    @Singleton
    public  MovieReviewsDataSource providesMovieReviewsDataSource() {
        return new MovieReviewsRestApiDataSource();
    }

}
