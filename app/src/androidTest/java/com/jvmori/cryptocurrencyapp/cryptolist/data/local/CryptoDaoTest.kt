package com.jvmori.cryptocurrencyapp.cryptolist.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jvmori.cryptocurrencyapp.util.TestUtil
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

class CryptoDaoTest {
    private lateinit var cryptoDao: CryptoDao
    private lateinit var cryptoDb: CryptoDatabase

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        cryptoDb = Room.inMemoryDatabaseBuilder(
            context, CryptoDatabase::class.java
        ).build()
        cryptoDao = cryptoDb.cryptoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        cryptoDb.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCryptocurrencies_readFromDb_cryptocurrenciesNotEmpty() {
        val cryptocurrencies = TestUtil.cryptocurrenciesDefaultOrder
        cryptoDao.insert(cryptocurrencies)

        fun getQuery(sort: String): SimpleSQLiteQuery {
            return SimpleSQLiteQuery("Select * from crypto_table order by ? ASC", arrayOf(sort))
        }

        val getCryptocurrencyList =
            cryptoDao.getCryptocurrencies(getQuery(cryptoName))

        assertNotNull(getCryptocurrencyList)
    }

    @Test
    @Throws(Exception::class)
    fun insertCryptocurrencies_readFromDb_defaultOrderAsc() {
        val cryptocurrencies = TestUtil.cryptocurrenciesDefaultOrder
        cryptoDao.insert(cryptocurrencies)

        fun getQuery(sort: String): SimpleSQLiteQuery {
            return SimpleSQLiteQuery("Select * from crypto_table order by ? ASC", arrayOf(sort))
        }

        val getCryptocurrencyList =
            cryptoDao.getCryptocurrencies(getQuery(cryptoName))

        assertEquals(cryptocurrencies[0].name, getCryptocurrencyList.blockingFirst()[0].name)
        assertEquals(cryptocurrencies[1].name, getCryptocurrencyList.blockingFirst()[1].name)
    }

    @Test
    @Throws(Exception::class)
    fun insertCryptocurrencies_readFromDb_volumeOrderAsc() {
        val cryptocurrencies = TestUtil.cryptocurrenciesVolumeOrder
        cryptoDao.insert(cryptocurrencies)

        fun getQuery(sort: String): SimpleSQLiteQuery {
            return SimpleSQLiteQuery("Select * from crypto_table order by ? ASC", arrayOf(sort))
        }

        val getCryptocurrencyList =
            cryptoDao.getCryptocurrencies(getQuery(cryptoVolume))

        assertEquals(cryptocurrencies[0].name, getCryptocurrencyList.blockingFirst()[0].name)
        assertEquals(cryptocurrencies[1].name, getCryptocurrencyList.blockingFirst()[1].name)
    }

    @Test
    @Throws(Exception::class)
    fun insertCryptocurrencies_readFromDb_percentChangeOrderAsc() {
        val cryptocurrencies = TestUtil.cryptocurrenciesPercentChangeOrder
        cryptoDao.insert(cryptocurrencies)

        fun getQuery(sort: String): SimpleSQLiteQuery {
            return SimpleSQLiteQuery("Select * from crypto_table order by ? ASC", arrayOf(sort))
        }

        val getCryptocurrencyList =
            cryptoDao.getCryptocurrencies(getQuery(cryptoPercentChange24))

        assertEquals(cryptocurrencies[0].name, getCryptocurrencyList.blockingFirst()[0].name)
        assertEquals(cryptocurrencies[1].name, getCryptocurrencyList.blockingFirst()[1].name)
    }
}