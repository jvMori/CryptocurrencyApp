package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.cryptoName
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock

class GetCryptoCurrenciesUseCaseImplTest{

    private val repository =mock(CryptocurrencyRepository::class.java)
    private val cryptoListUseCases by lazy { GetCryptoCurrenciesUseCaseImpl(repository) }

    @Test
    fun testCryptoListUseCases_getCryptoList_Completed(){
        Mockito.`when`(repository.getCryptocurrencies(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(emptyList()))

        cryptoListUseCases.getCryptocurrencies(cryptoName)
            .test()
            .assertComplete()
    }

    @Test
    fun testCryptoListUseCases_getCryptoList_Error(){
        val response = Throwable("Error response")
        Mockito.`when`(repository.getCryptocurrencies(ArgumentMatchers.anyString()))
            .thenReturn(Observable.error(response))

        cryptoListUseCases.getCryptocurrencies(cryptoName)
            .test()
            .assertError(response)
    }

    @Test
    fun testCryptoListUseCases_getCryptoList_response(){
        val cryptoData = mock(CryptocurrencyEntity::class.java)
        val response = arrayListOf(cryptoData)
        Mockito.`when`(repository.getCryptocurrencies(ArgumentMatchers.anyString()))
            .thenReturn(Observable.just(response))

        cryptoListUseCases.getCryptocurrencies(cryptoName)
            .test()
            .assertValue(response)
    }
}