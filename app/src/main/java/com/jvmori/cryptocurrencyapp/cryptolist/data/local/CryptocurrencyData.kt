package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val cryptoName = "crypto_name"

@Entity(tableName = "crypto_table")
data class CryptocurrencyData(
    @ColumnInfo(name = "crypto_id")
    @PrimaryKey(autoGenerate = false)
    var id: String = "",
    @ColumnInfo(name = cryptoName)
    var name: String = "",
    @ColumnInfo(name = "percent_change_1h")
    var percentChange1h: Double = 0.00,
    @ColumnInfo(name = "percent_change_24h")
    var percentChange24h: Double = 0.00,
    @ColumnInfo(name = "price_usd")
    var priceUsd: Double = 0.00,
    @ColumnInfo(name = "symbol")
    var symbol: String = "",
    @ColumnInfo(name = "volume")
    var volume: Double = 0.0
)