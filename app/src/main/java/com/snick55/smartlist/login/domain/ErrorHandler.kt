package com.snick55.smartlist.login.domain


import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.snick55.smartlist.core.AppExceptions
import com.snick55.smartlist.core.log
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

interface ErrorHandler {

    fun handle(e: Exception): AppExceptions

    class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

        override fun handle(e: Exception): AppExceptions{
            val appException = when (e) {
                is UnknownHostException -> NoInternetException()
                is FirebaseAuthMissingActivityForRecaptchaException -> RecaptchaException()
                is FirebaseAuthInvalidCredentialsException -> InvalidRequestException()
                is FirebaseNetworkException -> NoInternetException()
                is FirebaseAuthException -> ServerUnavailableException()
                is AppExceptions -> e
                else -> GenericException()
            }
            log("$appException")
            return appException
        }
    }

}