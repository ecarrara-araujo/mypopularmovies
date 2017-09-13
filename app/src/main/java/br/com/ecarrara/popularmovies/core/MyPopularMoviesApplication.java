package br.com.ecarrara.popularmovies.core;

import android.app.Application;

import br.com.ecarrara.popularmovies.core.di.Injector;

public class MyPopularMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.initialize(this);
    }

}
