package com.jvmori.cryptocurrencyapp.cryptolist.data.repositories

import androidx.work.*
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.CryptoWorker
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.roundTo
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

class CryptocurrencyRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val workManager: WorkManager,
    private val workRequest: PeriodicWorkRequest
) : CryptocurrencyRepository {

    override fun getCryptocurrencies(sort: String): Observable<List<CryptocurrencyEntity>> {
        return localDataSource.getCryptocurrencies(sort).map {
            mapper(it)
        }
    }

    override fun refreshPeriodically() {
        workManager.enqueue(workRequest)
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