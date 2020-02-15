package com.jvmori.cryptocurrencyapp.cryptolist.data.remote


import com.google.gson.annotations.SerializedName

data class Cryptocurrency(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("percent_change_1h")
    var percentChange1h: Double = 0.00,
    @SerializedName("percent_change_24h")
    var percentChange24h: Double = 0.00,
    @SerializedName("price_usd")
    var priceUsd: Double = 0.00,
    @SerializedName("symbol")
    var symbol: String = "",
    @SerializedName("volume24")
    var volume24: Double = 0.0
)