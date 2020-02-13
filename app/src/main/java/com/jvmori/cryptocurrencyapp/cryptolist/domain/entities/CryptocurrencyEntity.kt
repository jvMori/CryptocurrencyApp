package com.jvmori.cryptocurrencyapp.cryptolist.domain.entities

data class CryptocurrencyEntity(
    var cryptoName: String,
    var symbol: String,
    var priceUSD: Float,
    var percentChangeHour: Float,
    var percentChange24Hours: Float,
    var supply: Float
)