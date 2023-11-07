package com.snick55.smartlist.di.login

import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.login.data.LoginDataSource
import com.snick55.smartlist.login.data.LoginRepositoryImpl
import com.snick55.smartlist.login.domain.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindLoginRepository(repository: LoginRepositoryImpl): LoginRepository

    @Binds
    abstract fun bindLoginDataSource(dataSource: LoginDataSource.FireBaseLoginSource): LoginDataSource



}