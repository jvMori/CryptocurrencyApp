package com.jvmori.cryptocurrencyapp.util

import com.jvmori.cryptocurrencyapp.cryptolist.data.local.CryptocurrencyData

class TestUtil {
    companion object {
        private val crypto1 = CryptocurrencyData(
            "90",
            "Bitcoin",
            0.50,
            1.12,
            10238.90,
            "BTC",
            37345838108.48439
        )
        private val crypto2 = CryptocurrencyData(
            "80",
            "Ethereum",
            0.40,
            1.12,
            10238.90,
            "ETH",
            37345838108.48439
        )
        val cryptocurrencies = arrayListOf<CryptocurrencyData>(crypto1, crypto2)
    }
}