package com.jvmori.cryptocurrencyapp.cryptolist.data.util

fun Double.roundTo(n : Int) : Double {
    return "%.${n}f".format(this).toDouble()
}