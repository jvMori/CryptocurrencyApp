package com.jvmori.cryptocurrencyapp.cryptolist.domain.entities

data class CryptocurrencyEntity(
    var cryptoName: String = "",
    var symbol: String = "",
    var priceUSD: Double,
    var percentChangeHour: Double,
    var percentChange24Hours: Double,
    var volume: Double
)