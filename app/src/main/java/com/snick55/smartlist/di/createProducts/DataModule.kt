package com.snick55.smartlist.di.createProducts

import com.snick55.smartlist.createProducts.data.ProductsRepositoryImpl
import com.snick55.smartlist.createProducts.domain.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindProductsRepository(repository: ProductsRepositoryImpl): ProductsRepository

}