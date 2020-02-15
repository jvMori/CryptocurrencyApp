package com.jvmori.cryptocurrencyapp.cryptolist.presentation.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import com.jvmori.cryptocurrencyapp.R
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.cryptoName
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels.CryptocurrencyListViewModel
import com.jvmori.cryptocurrencyapp.databinding.FragmentCryptocurrencyListBinding
import org.koin.android.viewmodel.ext.android.viewModel

class CryptocurrencyListFragment : Fragment() {

    private val cryptoViewModel: CryptocurrencyListViewModel by viewModel()
    private lateinit var binding: FragmentCryptocurrencyListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cryptocurrency_list, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        cryptoViewModel.apply {
            fetchCryptocurrencies(cryptoName)
            cryptocurrencies.observe(this@CryptocurrencyListFragment, Observer {
                when (it.status) {
                    Resource.Status.LOADING -> showLoading()
                    Resource.Status.SUCCESS -> showList(it.data)
                    Resource.Status.ERROR -> showErrorView()
                }
            })
        }
    }

    private fun showErrorView() {
        binding.loading.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun showList(cryptocurrencies: List<CryptocurrencyEntity>?) {
        binding.loading.visibility = View.GONE
    }

}
