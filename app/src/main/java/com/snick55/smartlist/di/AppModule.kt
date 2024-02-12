package com.snick55.smartlist.di

import com.snick55.smartlist.core.DateFormater
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.PreferenceSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindFirebaseProvider(firebaseProviderImpl: FirebaseProvider.FirebaseProviderImpl): FirebaseProvider

    @Binds
    abstract fun bindFirebaseDatabaseProvider(firebase: FirebaseDatabaseProvider.FirebaseDatabaseProviderImpl): FirebaseDatabaseProvider

    @Binds
    abstract fun bindDateFormater(dateFormater: DateFormater.DateFormaterImpl): DateFormater

    @Binds
    abstract fun bindPreferenceSource(preferenceSource: PreferenceSource.NamePreferenceSource): PreferenceSource<String>

}