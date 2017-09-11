package br.com.ecarrara.popularmovies.core.networking.connectivity;

import io.reactivex.Observable;

public interface ConnectivityObserver {

    Observable<Boolean> observeConnectivity();

}
