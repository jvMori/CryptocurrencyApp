package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository

class RefreshCryptocurrenciesUseCaseImpl(
    private val repository: CryptocurrencyRepository
) : RefreshCryptocurrenciesUseCase {
    override fun refreshPeriodically() {
       repository.refreshPeriodically()
    }
}