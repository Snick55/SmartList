package com.snick55.smartlist.login.data

import com.snick55.smartlist.login.domain.LoginRepository
import com.snick55.smartlist.login.domain.entities.PhoneRequestWrapper
import com.snick55.smartlist.login.domain.entities.SignInWrapper
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val firebaseDataSource: LoginDataSource,
): LoginRepository {

    override suspend fun getCode(phoneRequestWrapper: PhoneRequestWrapper) {
        firebaseDataSource.getCodeByNumber(phoneRequestWrapper.phoneNumber,phoneRequestWrapper.activity,phoneRequestWrapper.callback)
    }

    override suspend fun signIn(signInData: SignInWrapper) {
        firebaseDataSource.signIn(signInData.code,signInData.verificationId)
    }
}