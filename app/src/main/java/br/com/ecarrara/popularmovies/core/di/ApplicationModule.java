package br.com.ecarrara.popularmovies.core.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.core.MyPopularMoviesApplication;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class ApplicationModule {

    private MyPopularMoviesApplication applicationContext;

    public ApplicationModule(@NonNull MyPopularMoviesApplication applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Provides
    @Singleton
    public Context providesApplicationContext() {
        return this.applicationContext;
    }

}
