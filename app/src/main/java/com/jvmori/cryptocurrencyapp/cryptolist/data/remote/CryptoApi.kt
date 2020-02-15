package com.jvmori.cryptocurrencyapp.cryptolist.data.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {
    @GET("tickers/")
    fun getCryptocurrencies(@Query("limit") limit : Int = 20) : Observable<CryptoResponse>
}