package com.jvmori.cryptocurrencyapp.cryptolist.data.remote

import io.reactivex.Observable

interface RemoteDataSource {
    fun getCryptocurrencies() : Observable<CryptoResponse>
}