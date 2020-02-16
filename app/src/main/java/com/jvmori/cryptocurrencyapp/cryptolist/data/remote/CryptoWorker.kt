package com.jvmori.cryptocurrencyapp.cryptolist.data.remote

import android.content.Context
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.mapResponseToLocal
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

class CryptoWorker(
    context: Context,
    parameters: WorkerParameters
) : RxWorker(context, parameters), KoinComponent {

    private val localDataSource: LocalDataSource by inject()
    private val remoteDataSource: RemoteDataSource by inject()

    override fun createWork(): Single<Result> {
        return Observable.range(0, 1)
            .flatMap {
                remoteDataSource.getCryptocurrencies()
            }.doOnError {
                Result.retry()
            }.map {
                localDataSource.updateCryptocurrencies(mapResponseToLocal(it))
            }.toList().map {
                Result.success()
            }
    }
}