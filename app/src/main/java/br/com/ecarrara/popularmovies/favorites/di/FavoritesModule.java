package br.com.ecarrara.popularmovies.favorites.di;

import android.content.Context;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.favorites.data.datasource.contentprovider.FavoritesProviderLocalDataSource;
import br.com.ecarrara.popularmovies.favorites.domain.data.FavoritesLocalDataSource;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class FavoritesModule {

    @Provides
    @Singleton
    public FavoritesLocalDataSource providesFavoritesLocalDataSource(Context context) {
        return new FavoritesProviderLocalDataSource(context);
    }

}
