package com.jvmori.cryptocurrencyapp.util

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData

class TestUtil {
    companion object {
        private val crypto1 = CryptocurrencyData(
            "90",
            "Bitcoin",
            0.50,
            -0.12,
            10238.90,
            "BTC",
            37345838108.48439
        )
        private val crypto2 = CryptocurrencyData(
            "80",
            "Ethereum",
            0.40,
            4.28,
            10238.90,
            "ETH",
            21646561084.177418
        )
        val cryptocurrenciesDefaultOrder = arrayListOf(crypto1, crypto2)
        val cryptocurrenciesVolumeOrder = arrayListOf(crypto2, crypto1)
        val cryptocurrenciesPercentChangeOrder = arrayListOf(crypto1, crypto2)
    }
}