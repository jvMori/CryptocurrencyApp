package com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptocurrenciesUseCase
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.RefreshCryptocurrenciesUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CryptocurrencyListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CryptocurrencyListViewModel
    private val getCryptoCurrenciesUseCase = mock(GetCryptocurrenciesUseCase::class.java)
    private val refreshCryptocurrenciesUseCase = mock(RefreshCryptocurrenciesUseCase::class.java)
    private val disposable = CompositeDisposable()

    @Mock
    lateinit var observer: Observer<Resource<List<CryptocurrencyEntity>>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = CryptocurrencyListViewModel(getCryptoCurrenciesUseCase, refreshCryptocurrenciesUseCase, disposable)
    }

    @Test
    fun getCryptocurrencies_success() {
        `when`(getCryptoCurrenciesUseCase.getCryptocurrencies(ArgumentMatchers.anyString()))
            .thenAnswer {
                return@thenAnswer Observable.just(ArgumentMatchers.anyList<CryptocurrencyEntity>())
            }

        viewModel.cryptocurrencies.observeForever(observer)
        viewModel.fetchLocalCryptocurrencies(ArgumentMatchers.anyString())

        assert(viewModel.cryptocurrencies.value != null)
        assert(Resource.Status.SUCCESS == viewModel.cryptocurrencies.value?.status)
    }

}