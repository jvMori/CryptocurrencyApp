package com.jvmori.cryptocurrencyapp.cryptolist.presentation.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.jvmori.cryptocurrencyapp.R
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.cryptoName
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels.CryptocurrencyListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class CryptocurrencyListFragment : Fragment() {

    private val cryptoViewModel : CryptocurrencyListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cryptocurrency_list, container, false)
    }

    override fun onStart() {
        super.onStart()
        cryptoViewModel.apply {
            fetchCryptocurrencies(cryptoName)
            cryptocurrencies.observe(this@CryptocurrencyListFragment, Observer {
                when (it.status){
                    Resource.Status.LOADING -> showProgressBar()
                    Resource.Status.SUCCESS -> showList(it.data)
                    Resource.Status.ERROR -> showErrorView()
                }
            })
        }
    }

    private fun showErrorView() {

    }

    private fun showProgressBar() {

    }

    private fun showList(cryptocurrencies : List<CryptocurrencyEntity>?) {

    }

}
