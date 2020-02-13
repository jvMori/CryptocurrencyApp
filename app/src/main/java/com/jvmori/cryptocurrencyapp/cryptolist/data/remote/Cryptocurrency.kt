package com.jvmori.cryptocurrencyapp.cryptolist.data.remote


import com.google.gson.annotations.SerializedName

data class Cryptocurrency(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("percent_change_1h")
    var percentChange1h: String = "",
    @SerializedName("percent_change_24h")
    var percentChange24h: String = "",
    @SerializedName("price_usd")
    var priceUsd: String = "",
    @SerializedName("symbol")
    var symbol: String = "",
    @SerializedName("volume24")
    var volume24: Double = 0.0
)