package com.snick55.smartlist.di.lists

import com.snick55.smartlist.lists.data.DetailsDataSource
import com.snick55.smartlist.lists.data.DetailsRepositoryImpl
import com.snick55.smartlist.lists.data.ListsDataSource
import com.snick55.smartlist.lists.data.ListsRepositoryImpl
import com.snick55.smartlist.lists.domain.DetailsRepository
import com.snick55.smartlist.lists.domain.ListsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindListsRepository(repository: ListsRepositoryImpl): ListsRepository

    @Binds
    abstract fun bindListsDataSource(source: ListsDataSource.ListsDataSourceImpl): ListsDataSource

    @Binds
    abstract fun bindDetailsRepository(repository: DetailsRepositoryImpl):DetailsRepository

    @Binds
    abstract fun bindDetailsDataSource(dataSource: DetailsDataSource.DetailsDataSourceImpl): DetailsDataSource
}