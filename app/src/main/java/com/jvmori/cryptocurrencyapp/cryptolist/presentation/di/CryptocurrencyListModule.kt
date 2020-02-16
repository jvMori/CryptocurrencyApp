package com.jvmori.cryptocurrencyapp.cryptolist.presentation.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptoDatabase
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSourceImpl
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.CryptoWorker
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.RemoteDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.RemoteDataSourceImpl
import com.jvmori.cryptocurrencyapp.cryptolist.data.repositories.CryptocurrencyRepositoryImpl
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptoCurrenciesUseCaseImpl
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptocurrenciesUseCase
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.RefreshCryptocurrenciesUseCase
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.RefreshCryptocurrenciesUseCaseImpl
import com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels.CryptocurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val cryptocurrencyListModule = module {
    single<RemoteDataSource> { RemoteDataSourceImpl(get()) }
    single<LocalDataSource> { LocalDataSourceImpl((get() as CryptoDatabase).cryptoDao()) }
    single<CryptocurrencyRepository> { CryptocurrencyRepositoryImpl(get(), get(), get()) }
    single<GetCryptocurrenciesUseCase> { GetCryptoCurrenciesUseCaseImpl(get()) }
    single<RefreshCryptocurrenciesUseCase> { RefreshCryptocurrenciesUseCaseImpl(get()) }
    single { work }
    single { WorkManager.getInstance(get()) }
    viewModel { CryptocurrencyListViewModel(get(), get()) }
}

private val constraints = Constraints.Builder()
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

private val work = PeriodicWorkRequestBuilder<CryptoWorker>(30, TimeUnit.SECONDS)
    .setConstraints(constraints)
    .build()
