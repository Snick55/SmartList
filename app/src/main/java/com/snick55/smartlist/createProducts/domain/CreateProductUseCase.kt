package com.snick55.smartlist.createProducts.domain

import javax.inject.Inject

interface CreateProductUseCase {

    suspend fun execute(name: String,count:String?,dateFrom:String?,dateTo:String?)


    class CreateProductUseCaseImpl @Inject constructor(
        private val repository: ProductsRepository
    ): CreateProductUseCase{

        override suspend fun execute(
            name: String,
            count: String?,
            dateFrom: String?,
            dateTo: String?
        ) {
            repository.createProduct(name,count?:"",dateFrom?:"",dateTo?:"")
        }
    }

}