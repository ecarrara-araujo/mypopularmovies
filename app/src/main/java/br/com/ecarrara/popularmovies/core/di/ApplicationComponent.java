package br.com.ecarrara.popularmovies.core.di;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.core.networking.di.NetworkingModule;
import br.com.ecarrara.popularmovies.favorites.di.FavoritesModule;
import br.com.ecarrara.popularmovies.movies.di.MoviesModule;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieDetailActivity;
import br.com.ecarrara.popularmovies.movies.presentation.view.MovieListActivity;
import br.com.ecarrara.popularmovies.reviews.di.ReviewsModule;
import br.com.ecarrara.popularmovies.reviews.presentation.view.MovieReviewsFragment;
import br.com.ecarrara.popularmovies.trailers.di.TrailersModule;
import br.com.ecarrara.popularmovies.trailers.presentation.view.TrailerListFragment;
import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkingModule.class,
        MoviesModule.class,
        ReviewsModule.class,
        TrailersModule.class,
        FavoritesModule.class
})
public interface ApplicationComponent {
    void inject(MovieDetailActivity movieDetailActivity);
    void inject(MovieReviewsFragment movieReviewsFragment);
    void inject(TrailerListFragment trailerListFragment);
    void inject(MovieListActivity movieListActivity);
}
