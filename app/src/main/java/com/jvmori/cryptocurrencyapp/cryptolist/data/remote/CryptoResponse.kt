package com.jvmori.cryptocurrencyapp.cryptolist.data.remote


import com.google.gson.annotations.SerializedName

data class CryptoResponse(
    @SerializedName("data")
    var cryptoData: List<Cryptocurrency> = listOf()
)