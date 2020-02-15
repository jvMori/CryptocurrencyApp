package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import io.reactivex.Observable

interface LocalDataSource {
    fun getCryptocurrencies(sort : String) : Observable<List<CryptocurrencyData>>
}