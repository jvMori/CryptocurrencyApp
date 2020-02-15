package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import androidx.sqlite.db.SimpleSQLiteQuery

fun getCryptocurrenciesBy(sort: String): SimpleSQLiteQuery {
    return SimpleSQLiteQuery("Select * from crypto_table order by ? ASC", arrayOf(sort))
}