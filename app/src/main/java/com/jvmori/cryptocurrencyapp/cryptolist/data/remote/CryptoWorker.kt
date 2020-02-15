package com.jvmori.cryptocurrencyapp.cryptolist.data.remote

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.LocalDataSource
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.mapResponseToLocal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class CryptoWorker(
    context: Context,
    parameters: WorkerParameters,
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val disposable: CompositeDisposable
) : Worker(context, parameters) {

    override fun doWork(): Result {
        return try {
            disposable.add(
                remoteDataSource.getCryptocurrencies()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnNext {
                        localDataSource.updateCryptocurrencies(mapResponseToLocal(it))
                    }
                    .doOnError { Result.retry() }
                    .subscribe()
            )
            Result.success()
        } catch (throwable: Throwable) {
            Result.failure()
        }
    }
}