package com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.cryptoName
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface CryptocurrencyRepository {
    fun getCryptocurrencies(sort : String = cryptoName) : Observable<List<CryptocurrencyEntity>>
    fun refreshPeriodically()
    fun getNetworkStatus() : PublishSubject<Resource.Status>
}