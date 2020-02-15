package com.jvmori.cryptocurrencyapp.cryptolist.presentation.di

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptoDatabase
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSourceImpl
import com.jvmori.cryptocurrencyapp.cryptolist.data.repositories.CryptocurrencyRepositoryImpl
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptoCurrenciesUseCaseImpl
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptocurrenciesUseCase
import com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels.CryptocurrencyListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val cryptocurrencyListModule = module {
    single<LocalDataSource> { LocalDataSourceImpl((get() as CryptoDatabase).cryptoDao()) }
    single<CryptocurrencyRepository> { CryptocurrencyRepositoryImpl(get()) }
    single<GetCryptocurrenciesUseCase> { GetCryptoCurrenciesUseCaseImpl(get()) }
    viewModel { CryptocurrencyListViewModel(get()) }
}