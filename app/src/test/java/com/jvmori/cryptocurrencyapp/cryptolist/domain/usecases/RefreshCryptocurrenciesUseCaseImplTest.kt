package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

class RefreshCryptocurrenciesUseCaseImplTest {

    private val repository = mock(CryptocurrencyRepository::class.java)
    private val cryptoListUseCases = RefreshCryptocurrenciesUseCaseImpl(repository)

    @Before
    fun setUp() = RxJavaPlugins.reset()

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun testMyObservable() {
        val testScheduler = TestScheduler()
        val testObserver = TestObserver<List<CryptocurrencyEntity>>()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        val result = arrayListOf(mock(CryptocurrencyEntity::class.java))

        Mockito.`when`(cryptoListUseCases.refreshPeriodically())
            .thenReturn(Observable.just(result))

        cryptoListUseCases.refreshPeriodically().subscribeWith(testObserver)

        testScheduler.advanceTimeBy(30, TimeUnit.SECONDS)
        testObserver.assertValueCount(1)
        testObserver.assertValue(result)
    }
}