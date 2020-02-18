package com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels

import android.accounts.NetworkErrorException
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
import java.lang.Exception
import java.net.UnknownHostException

class CryptocurrencyListViewModel(
    private val cryptocurrencyListUseCase: GetCryptocurrenciesUseCase,
    private val refreshCryptocurrenciesUseCase: RefreshCryptocurrenciesUseCase,
    private val disposable: CompositeDisposable
) : ViewModel() {

    private val _networkStatus = MutableLiveData<Resource.Status>()
    val networkStatus: LiveData<Resource.Status>
        get() = _networkStatus
    private val _cryptocurrencies = MutableLiveData<Resource<List<CryptocurrencyEntity>>>()
    val cryptocurrencies: LiveData<Resource<List<CryptocurrencyEntity>>>
        get() = _cryptocurrencies

    fun fetchLocalCryptocurrencies(sort: String) {
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
        _networkStatus.postValue(Resource.Status.LOADING)
        disposable.add(
            refreshCryptocurrenciesUseCase.refreshPeriodically().subscribe(
                {
                    _networkStatus.postValue(Resource.Status.SUCCESS)
                }, {
                    handleError(it)
                }
            )
        )
    }

    private fun handleError(it: Throwable?) {
        try {
            if (it is UnknownHostException || it is NetworkErrorException) {
                _networkStatus.postValue(Resource.Status.NETWORK_ERROR)
            } else {
                _networkStatus.postValue(Resource.Status.ERROR)
            }
        } catch (e: UnknownHostException) {

        } catch (e: NetworkErrorException) {

        } catch (e: Exception) {

        }
    }

    fun clearPeriodicRefresh() {
        disposable.clear()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}