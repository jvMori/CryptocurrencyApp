package com.jvmori.cryptocurrencyapp.cryptolist.data.remote

import io.reactivex.Observable

class RemoteDataSourceImpl(
    private val api: CryptoApi
) : RemoteDataSource {
    override fun getCryptocurrencies(): Observable<CryptoResponse> {
        return api.getCryptocurrencies()
    }
}