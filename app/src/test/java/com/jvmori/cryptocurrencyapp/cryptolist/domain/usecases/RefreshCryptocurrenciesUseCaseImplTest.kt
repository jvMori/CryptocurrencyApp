package com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases

import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.util.concurrent.TimeUnit

class RefreshCryptocurrenciesUseCaseImplTest {

    private val repository = mock(CryptocurrencyRepository::class.java)
    private val cryptoListUseCases by lazy { RefreshCryptocurrenciesUseCaseImpl(repository) }

    @Before
    fun setUp() = RxJavaPlugins.reset()

    @After
    fun tearDown() = RxJavaPlugins.reset()

    @Test
    fun testMyObservable() {
        val testScheduler = TestScheduler()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        val result = arrayListOf(mock(CryptocurrencyEntity::class.java))

        val testObservable = cryptoListUseCases.refreshPeriodically().test()
        cryptoListUseCases.refreshPeriodically().subscribe()

        testScheduler.advanceTimeBy(0, TimeUnit.SECONDS)
        testObservable.assertEmpty()
        testScheduler.advanceTimeBy(30, TimeUnit.SECONDS)
        testObservable.assertValue(result)
    }
}