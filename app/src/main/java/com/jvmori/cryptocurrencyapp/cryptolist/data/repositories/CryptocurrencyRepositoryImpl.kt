package com.jvmori.cryptocurrencyapp.cryptolist.data.repositories

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.mapResponseToLocal
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.RemoteDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.roundTo
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.lang.Exception
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

class CryptocurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val disposable: CompositeDisposable
) : CryptocurrencyRepository {

    private var networkStatusObservable: PublishSubject<Resource.Status> =
        PublishSubject.create()

    override fun getNetworkStatus(): PublishSubject<Resource.Status> = networkStatusObservable

    override fun getCryptocurrencies(sort: String): Observable<List<CryptocurrencyEntity>> {
        return localDataSource.getCryptocurrencies(sort).map {
            mapper(it)
        }
    }

    override fun refreshPeriodically() {
        disposable.add(
            Observable.interval(0, 30, TimeUnit.SECONDS)
                .flatMap {
                    remoteDataSource.getCryptocurrencies()
                }.map {
                    localDataSource.updateCryptocurrencies(mapResponseToLocal(it))
                }.subscribe({
                    networkStatusObservable.onNext(Resource.Status.SUCCESS)
                }, {
                    handleError(it)
                })
        )
    }

    private fun handleError(it: Throwable?) {
        try {
            if (it is UnknownHostException || it is NetworkErrorException) {
                networkStatusObservable.onNext(Resource.Status.NETWORK_ERROR)
            } else {
                networkStatusObservable.onNext(Resource.Status.ERROR)
            }
        } catch (e: UnknownHostException) {

        } catch (e: NetworkErrorException) {

        } catch (e: Exception) {

        }
    }

    private fun mapper(cryptocurrencyData: List<CryptocurrencyData>): List<CryptocurrencyEntity> {
        return cryptocurrencyData.map {
            CryptocurrencyEntity(
                it.name,
                it.symbol,
                it.priceUsd.roundTo(2),
                it.percentChange1h.roundTo(2),
                it.percentChange24h.roundTo(2),
                (it.volume / 1000000).roundTo(2)
            )
        }
    }


}