package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable

class GetCryptoCurrencyListImpl(
    private val repository: CryptocurrencyRepository
) : GetCryptocurrencyList {
    override fun getCryptocurrencies(): Observable<List<CryptocurrencyEntity>> {
        return repository.getCryptocurrencies()
    }
}