package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable

class GetCryptoCurrencyListUseCaseImpl(
    private val repository: CryptocurrencyRepository
) : GetCryptocurrencyListUseCase {
    override fun getCryptocurrencies(sort : String): Observable<List<CryptocurrencyEntity>> {
        return repository.getCryptocurrencies(sort)
    }
}