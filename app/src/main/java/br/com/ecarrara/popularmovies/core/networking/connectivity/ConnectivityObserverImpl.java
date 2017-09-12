package br.com.ecarrara.popularmovies.core.networking.connectivity;

import android.content.Context;
import android.net.NetworkInfo;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class ConnectivityObserverImpl implements ConnectivityObserver {

    private Context applicationContext;

    public ConnectivityObserverImpl(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Observable<Boolean> observeConnectivity() {
        return ReactiveNetwork.observeNetworkConnectivity(applicationContext)
                .observeOn(Schedulers.io())
                .flatMap(connectivity ->
                        Observable.just(connectivity.getState().equals(NetworkInfo.State.CONNECTED))
                );
    }

}
