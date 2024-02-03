package com.snick55.smartlist.di.createProducts

import com.snick55.smartlist.createProducts.domain.CreateProductUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindCreateProductUseCase(useCase: CreateProductUseCase.CreateProductUseCaseImpl): CreateProductUseCase

}