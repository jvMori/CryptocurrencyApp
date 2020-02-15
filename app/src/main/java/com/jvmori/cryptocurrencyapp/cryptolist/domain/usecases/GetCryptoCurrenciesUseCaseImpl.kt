package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable

class GetCryptoCurrenciesUseCaseImpl(
    private val repository: CryptocurrencyRepository
) : GetCryptocurrenciesUseCase {
    override fun getCryptocurrencies(sort : String): Observable<List<CryptocurrencyEntity>> {
        return repository.getCryptocurrencies(sort)
    }
}