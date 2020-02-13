package com.jvmori.cryptocurrencyapp.common

import android.app.Application
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CryptoApplication)
            modules(
                listOf(
                    databaseModule
                )
            )
        }
    }
}