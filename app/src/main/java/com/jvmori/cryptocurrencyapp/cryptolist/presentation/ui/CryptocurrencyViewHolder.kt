package com.jvmori.cryptocurrencyapp.cryptolist.presentation.ui

import androidx.recyclerview.widget.RecyclerView
import com.jvmori.cryptocurrencyapp.cryptolist.domain.entities.CryptocurrencyEntity
import com.jvmori.cryptocurrencyapp.databinding.CryptocurrencyItemBinding

class CryptocurrencyViewHolder(
    private val binding: CryptocurrencyItemBinding
) : RecyclerView.ViewHolder(binding.root){

    fun bind(cryptoItem : CryptocurrencyEntity){
        binding.cryptoItem = cryptoItem
        binding.executePendingBindings()
    }
}