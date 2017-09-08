package br.com.ecarrara.popularmovies.core.di;

import android.app.Application;

import br.com.ecarrara.popularmovies.core.MyPopularMoviesApplication;

public final class Injector {

    private Injector() { /* must not be constructed */ }

    private static ApplicationComponent applicationComponent;

    public static void initialize(MyPopularMoviesApplication application) {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public static ApplicationComponent applicationComponent() {
        return applicationComponent;
    }

}
