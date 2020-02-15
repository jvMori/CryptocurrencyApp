package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class LocalDataSourceImpl(
    private val dao: CryptoDao
) : LocalDataSource {
    override fun getCryptocurrencies(sort: String): Observable<List<CryptocurrencyData>> {
        return dao.getCryptocurrencies(getCryptocurrenciesBy(sort))
    }

    override fun updateCryptocurrencies(cryptocurrencies: List<CryptocurrencyData>) {
        Completable.fromAction {
            dao.update(cryptocurrencies)
        }.subscribeOn(Schedulers.io()).subscribe()
    }
}