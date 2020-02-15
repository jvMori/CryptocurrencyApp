package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import io.reactivex.Observable

class LocalDataSourceImpl(
    private val dao: CryptoDao
) : LocalDataSource {
    override fun getCryptocurrencies(sort: String): Observable<List<CryptocurrencyData>> {
        return dao.getCryptocurrencies(getCryptocurrenciesBy(sort))
    }
}