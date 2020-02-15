package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import io.reactivex.Observable

interface GetCryptocurrenciesUseCase {
    fun getCryptocurrencies(sort : String): Observable<List<CryptocurrencyEntity>>
}