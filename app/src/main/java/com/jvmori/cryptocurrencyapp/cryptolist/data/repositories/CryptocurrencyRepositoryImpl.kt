package com.jvmori.cryptocurrencyapp.cryptolist.data.repositories

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.mapResponseToLocal
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.RemoteDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.roundTo
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CryptocurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : CryptocurrencyRepository {

    override fun getCryptocurrencies(sort: String): Observable<List<CryptocurrencyEntity>> {
        return localDataSource.getCryptocurrencies(sort).map {
            mapper(it)
        }
    }

    override fun refreshPeriodically(): Observable<List<CryptocurrencyEntity>> {
        return Observable.interval(0, 30, TimeUnit.SECONDS)
            .flatMap {
                remoteDataSource.getCryptocurrencies()
            }.map {
                val data = mapResponseToLocal(it)
                localDataSource.updateCryptocurrencies(data)
                return@map mapper(data)
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