package com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.domain.usecases.GetCryptocurrencyListUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CryptocurrencyListViewModel(
    private val cryptocurrencyListUseCase : GetCryptocurrencyListUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _cryptocurrencies = MutableLiveData<Resource<List<CryptocurrencyEntity>>>()
    val cryptocurrencies: LiveData<Resource<List<CryptocurrencyEntity>>>
        get() = _cryptocurrencies

    fun fetchCryptocurrencies(sort: String) {
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

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}