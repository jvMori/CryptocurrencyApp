package com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.cryptoName
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import io.reactivex.Observable

interface CryptocurrencyRepository {
    fun getCryptocurrencies(sort : String = cryptoName) : Observable<List<CryptocurrencyEntity>>
    fun refreshPeriodically() : Observable<List<CryptocurrencyEntity>>
}