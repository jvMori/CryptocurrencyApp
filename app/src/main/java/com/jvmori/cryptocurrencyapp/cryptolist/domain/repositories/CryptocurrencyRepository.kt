package com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import io.reactivex.Observable

interface CryptocurrencyRepository {
    fun getCryptocurrencies() : Observable<List<CryptocurrencyEntity>>
}