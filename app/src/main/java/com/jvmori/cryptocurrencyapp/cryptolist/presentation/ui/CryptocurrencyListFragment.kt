package com.jvmori.cryptocurrencyapp.cryptolist.presentation.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.jvmori.cryptocurrencyapp.R
import com.jvmori.cryptocurrencyapp.cryptolist.data.local.cryptoName
import com.jvmori.cryptocurrencyapp.cryptolist.data.util.Resource
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.cryptolist.presentation.viemodels.CryptocurrencyListViewModel
import com.jvmori.cryptocurrencyapp.databinding.FragmentCryptocurrencyListBinding
import kotlinx.android.synthetic.main.cryptolist_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


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
            refreshCryptocurrencies()
            fetchCryptocurrencies(cryptoName)
        }
        observeCryptocurrencies()
        observeNetworkStatus()
    }

    private fun observeNetworkStatus() {
        cryptoViewModel.getNetworkStatus().observe(this@CryptocurrencyListFragment, Observer {
            when (it) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.NETWORK_ERROR -> showNetworkErrorInfo()
                Resource.Status.ERROR -> showErrorView()
            }
        })
    }

    private fun observeCryptocurrencies() {
        cryptoViewModel.cryptocurrencies.observe(this@CryptocurrencyListFragment, Observer {
            when (it.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> showList(it.data)
                Resource.Status.ERROR -> showErrorView()
            }
        })
    }
    private fun showNetworkErrorInfo() {
        Snackbar.make(this.requireView(),getString(R.string.network_error_message), Snackbar.LENGTH_LONG).show()
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
        val cryptoAdapter = CryptocurrencyAdapter(cryptocurrencies ?: arrayListOf())
        binding.mainLayout.recyclerView.apply {
            adapter = cryptoAdapter
            layoutManager = LinearLayoutManager(this@CryptocurrencyListFragment.requireContext(), RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
    }

    override fun onStop() {
        super.onStop()
        cryptoViewModel.clearPeriodicRefresh()
    }
}
