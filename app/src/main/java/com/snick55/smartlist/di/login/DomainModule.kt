package com.snick55.smartlist.di.login

import com.snick55.smartlist.login.domain.GetCodeByNumberUseCase
import com.snick55.smartlist.login.domain.PhoneUseCase
import com.snick55.smartlist.login.domain.SignInUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetCodeByNumberUseCase(useCase: GetCodeByNumberUseCase.GetCodeByNumberUseCaseImpl): GetCodeByNumberUseCase

    @Binds
    abstract fun bindSignInUseCase(useCase: SignInUseCase.SignInUseCaseImpl): SignInUseCase

    @Binds
    abstract fun bindPhoneUseCase(useCase: PhoneUseCase.PhoneUseCaseImpl):PhoneUseCase



}