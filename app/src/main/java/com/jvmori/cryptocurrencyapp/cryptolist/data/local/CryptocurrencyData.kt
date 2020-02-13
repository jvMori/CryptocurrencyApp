package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.ColumnInfo

data class CryptocurrencyData(
    @ColumnInfo(name = "crypto_id")
    var id: String = "",
    @ColumnInfo(name = "crypto_name")
    var name: String = "",
    @ColumnInfo(name = "percent_change_1h")
    var percentChange1h: String = "",
    @ColumnInfo(name = "percent_change_24h")
    var percentChange24h: String = "",
    @ColumnInfo(name = "price_usd")
    var priceUsd: String = "",
    @ColumnInfo(name = "symbol")
    var symbol: String = "",
    @ColumnInfo(name = "volume")
    var volume: Double = 0.0
)