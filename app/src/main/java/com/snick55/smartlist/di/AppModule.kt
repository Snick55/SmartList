package com.snick55.smartlist.di

import com.snick55.smartlist.core.FirebaseProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindFirebaseProvider(firebaseProviderImpl: FirebaseProvider.FirebaseProviderImpl): FirebaseProvider

}