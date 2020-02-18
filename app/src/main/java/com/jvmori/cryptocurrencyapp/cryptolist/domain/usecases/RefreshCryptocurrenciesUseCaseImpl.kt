package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable

class RefreshCryptocurrenciesUseCaseImpl(
    private val repository: CryptocurrencyRepository
) : RefreshCryptocurrenciesUseCase {
    override fun refreshPeriodically() : Observable<List<CryptocurrencyEntity>> {
       return repository.refreshPeriodically()
    }
}