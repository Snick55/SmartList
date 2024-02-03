package com.snick55.smartlist.createProducts.domain

interface ProductsRepository {

    suspend fun createProduct(name: String,count: String,dateFrom:String,dateTo:String)

}