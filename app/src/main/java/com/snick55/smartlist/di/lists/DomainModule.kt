package com.snick55.smartlist.di.lists

import com.snick55.smartlist.lists.domain.CreateNewListUseCase
import com.snick55.smartlist.lists.domain.GetAllItemsUseCase
import com.snick55.smartlist.lists.domain.GetAllListsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetAllListsUseCase(useCase: GetAllListsUseCase.GetAllListsUseCaseImpl): GetAllListsUseCase

    @Binds
    abstract fun bindCreateNewListUseCase(useCase: CreateNewListUseCase.CreateNewListUseCaseImpl): CreateNewListUseCase

    @Binds
    abstract fun bindGetAllItemsUseCase(useCase: GetAllItemsUseCase.GetAllItemsUseCaseImpl): GetAllItemsUseCase

}