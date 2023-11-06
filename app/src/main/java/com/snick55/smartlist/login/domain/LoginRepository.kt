package com.snick55.smartlist.login.domain

import com.snick55.smartlist.login.domain.entities.PhoneRequestWrapper
import com.snick55.smartlist.login.domain.entities.SignInWrapper

interface LoginRepository {

   suspend fun getCode(phoneRequestWrapper: PhoneRequestWrapper)

   suspend fun signIn(signInData: SignInWrapper)

}