package com.snick55.smartlist.di.profile

import com.snick55.smartlist.profile.data.ProfileDataSource
import com.snick55.smartlist.profile.data.ProfileRepositoryImpl
import com.snick55.smartlist.profile.domain.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindProfileRepository(repository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindCacheDataSource(cacheDataSource: ProfileDataSource.ProfileDataSourceImpl): ProfileDataSource

}