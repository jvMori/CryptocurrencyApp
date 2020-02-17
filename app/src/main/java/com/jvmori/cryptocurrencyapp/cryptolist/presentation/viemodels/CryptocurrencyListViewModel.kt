package com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.repositories.CryptocurrencyRepository
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptocurrenciesUseCase
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.RefreshCryptocurrenciesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CryptocurrencyListViewModel(
    private val repository: CryptocurrencyRepository,
    private val cryptocurrencyListUseCase: GetCryptocurrenciesUseCase,
    private val refreshCryptocurrenciesUseCase: RefreshCryptocurrenciesUseCase,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _cryptocurrencies = MutableLiveData<Resource<List<CryptocurrencyEntity>>>()
    val cryptocurrencies: LiveData<Resource<List<CryptocurrencyEntity>>>
        get() = _cryptocurrencies

    fun fetchCryptocurrenciesLocally(sort: String) {
        _cryptocurrencies.value = Resource.loading(null)
        disposable.add(
            cryptocurrencyListUseCase.getCryptocurrencies(sort)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ success ->
                    _cryptocurrencies.value = Resource.success(success)
                }, { error ->
                    _cryptocurrencies.value = Resource.error(error.localizedMessage ?: "", null)
                })
        )
    }

    fun refreshCryptocurrencies() {
        refreshCryptocurrenciesUseCase.refreshPeriodically()
    }

    fun getNetworkStatus(): LiveData<Resource.Status> {
        val status = MutableLiveData<Resource.Status>()
        disposable.add(
            repository.getNetworkStatus().subscribe {
                status.postValue(it)
            }
        )
        return status
    }

    fun clearPeriodicRefresh(){
        disposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}