package com.jvmori.cryptocurrencyapp.cryptolist.data.repositories

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable

class CryptocurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource
) : CryptocurrencyRepository {

    override fun getCryptocurrencies(sort: String): Observable<List<CryptocurrencyEntity>> {
        return localDataSource.getCryptocurrencies(sort).map {
            mapper(it)
        }
    }

    override fun refreshPeriodically() {

    }

    private fun mapper(cryptocurrencyData: List<CryptocurrencyData>): List<CryptocurrencyEntity> {
        return cryptocurrencyData.map {
            CryptocurrencyEntity(
                it.name,
                it.symbol,
                it.priceUsd,
                it.percentChange1h,
                it.percentChange24h,
                it.volume
            )
        }
    }
}