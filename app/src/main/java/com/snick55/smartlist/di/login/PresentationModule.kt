package com.snick55.smartlist.di.login

import com.snick55.smartlist.login.domain.ErrorHandler
import com.snick55.smartlist.login.presentation.LoginStateCommunication
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PresentationModule {


    @Binds
    abstract fun bindErrorHandler(errorHandler: ErrorHandler.ErrorHandlerImpl): ErrorHandler
    @Binds
    abstract fun bindLoginStateCommunication(communication: LoginStateCommunication.Base): LoginStateCommunication

}