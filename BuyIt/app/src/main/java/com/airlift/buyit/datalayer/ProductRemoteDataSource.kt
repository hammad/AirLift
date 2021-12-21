package com.airlift.buyit.datalayer

import com.airlift.buyit.data.Product
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProductRemoteDataSource (
    private val productApi: ProductApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchLatestProducts(): List<Product> =

        withContext(ioDispatcher) {
            productApi.fetchLatestProducts()
        }
}

interface ProductApi {
    fun fetchLatestProducts(): List<Product>
}