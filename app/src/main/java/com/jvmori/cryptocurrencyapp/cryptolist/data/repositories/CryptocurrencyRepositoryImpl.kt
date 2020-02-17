package com.jvmori.cryptocurrencyapp.cryptolist.data.repositories

import android.accounts.NetworkErrorException
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.mapResponseToLocal
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.RemoteDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.roundTo
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class CryptocurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val disposable: CompositeDisposable
) : CryptocurrencyRepository {

    private var networkStatus: Resource.Status = Resource.Status.LOADING

    override fun getNetworkStatus(): Resource.Status = networkStatus

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
                }.doOnError {
                    handleError(it)
                }.map {
                    localDataSource.updateCryptocurrencies(mapResponseToLocal(it))
                }.doOnComplete {
                    networkStatus = Resource.Status.SUCCESS
                }.subscribe()
        )
    }

    private fun handleError(it: Throwable?) {
        networkStatus = if (it is NetworkErrorException) {
            Resource.Status.NETWORK_ERROR
        } else {
            Resource.Status.ERROR
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