package com.airlift.buyit.datalayer

import com.airlift.buyit.data.Product
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ProductRepository (

    private val remoteDataSource:ProductRemoteDataSource
) {

    // Mutex to make writes to cached values thread-safe.
    private val latestProductsMutex = Mutex()

    // Cache of the latest products got from the network.
    private var latestProducts: List<Product> = emptyList()

    init {

    }

    suspend fun getLatestProducts(refresh: Boolean = false): List<Product> {
        if (refresh || latestProducts.isEmpty()) {
            val networkResult = remoteDataSource.fetchLatestProducts()

            latestProductsMutex.withLock {
                this.latestProducts = networkResult
            }
        }

        return latestProductsMutex.withLock { this.latestProducts }
    }
}