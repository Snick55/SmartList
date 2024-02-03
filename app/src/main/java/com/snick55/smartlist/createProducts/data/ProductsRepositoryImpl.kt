package com.snick55.smartlist.createProducts.data

import com.snick55.smartlist.createProducts.domain.ProductsRepository
import com.snick55.smartlist.lists.data.DetailsDataSource
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val detailsDataSource: DetailsDataSource
): ProductsRepository {

    override suspend fun createProduct(
        name: String,
        count: String,
        dateFrom: String,
        dateTo: String
    ) {
        detailsDataSource.createProduct(name,count,dateFrom,dateTo)
    }
}