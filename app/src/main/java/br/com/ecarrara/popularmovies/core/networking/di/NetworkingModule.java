package br.com.ecarrara.popularmovies.core.networking.di;

import android.content.Context;

import javax.inject.Singleton;

import br.com.ecarrara.popularmovies.core.networking.connectivity.ConnectivityObserver;
import br.com.ecarrara.popularmovies.core.networking.connectivity.ConnectivityObserverImpl;
import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class NetworkingModule {

    @Provides
    @Singleton
    public ConnectivityObserver providesConnectivityObservable(Context applicationContext) {
        return new ConnectivityObserverImpl(applicationContext);
    }

}
