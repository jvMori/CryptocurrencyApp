package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.sqlite.db.SimpleSQLiteQuery
import com.jvmori.cryptocurrencyapp.cryptolist.data.remote.CryptoResponse

fun getCryptocurrenciesBy(sort: String): SimpleSQLiteQuery {
    val query = "SELECT * FROM crypto_table ORDER BY " + sort +" ASC"
    return SimpleSQLiteQuery(query)
}

fun mapResponseToLocal(response: CryptoResponse) : List<CryptocurrencyData>{
    return response.cryptoData.map {
        CryptocurrencyData(
            it.id,
            it.name,
            it.percentChange1h,
            it.percentChange24h,
            it.priceUsd,
            it.symbol,
            it.volume24
        )
    }
}