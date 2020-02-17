package com.jvmori.cryptocurrencyapp.cryptolist.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.databinding.CryptocurrencyItemBinding

class CryptocurrencyAdapter(
    private var cryptocurrencies: List<CryptocurrencyEntity>
) : RecyclerView.Adapter<CryptocurrencyViewHolder>() {

    fun updateList(list : List<CryptocurrencyEntity>){
        cryptocurrencies = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CryptocurrencyItemBinding.inflate(inflater)
        return CryptocurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int = cryptocurrencies.size

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        val item = cryptocurrencies[position]
        holder.bind(item)
    }
}