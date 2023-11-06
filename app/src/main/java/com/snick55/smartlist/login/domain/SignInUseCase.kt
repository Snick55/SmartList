package com.snick55.smartlist.login.domain

import com.snick55.smartlist.login.domain.entities.SignInWrapper
import javax.inject.Inject

interface SignInUseCase {

   suspend fun execute(signInData: SignInWrapper)


   class SignInUseCaseImpl @Inject constructor (
       private val repository: LoginRepository
   ): SignInUseCase{
       override suspend fun execute(signInData: SignInWrapper) {
           repository.signIn(signInData)
       }
   }

}