package br.com.ecarrara.popularmovies.favorites.domain.data;

import java.util.List;

import br.com.ecarrara.popularmovies.favorites.domain.entity.Favorite;
import br.com.ecarrara.popularmovies.movies.domain.entity.Movie;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface FavoritesLocalDataSource {
    Completable save(Movie movie);
    Completable delete(Movie Movie);
    Single<List<Movie>> list();
    Single<Boolean> isFavorite(Movie movie);
}
