package com.snick55.smartlist.login.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.snick55.smartlist.R
import com.snick55.smartlist.core.App
import com.snick55.smartlist.login.domain.*
import java.lang.Exception
import javax.inject.Inject

interface LoginStateCommunication {

    fun map(e: AppExceptions)

    fun map(token: String)

    fun observe(owner: LifecycleOwner, observer: Observer<State>)

    fun showProgress()

    fun hideProgress()

    class Base @Inject constructor(): LoginStateCommunication {

        private val state = MutableLiveData(State())


        override fun map(token: String) {
            state.value = state.value?.copy(token = token)
        }

        override fun map(e: AppExceptions) {
            when (e) {
                is EmptyFieldException -> handleEmptyFieldError(e)
                is InvalidRequestException -> handleInvalidException()
                is RecaptchaException -> handleRecaptchaException()
                is NoInternetException -> handleNoInternetException()
                else -> handleInvalidException()
            }
            hideProgress()
        }

        override fun observe(
            owner: LifecycleOwner,
            observer: Observer<State>
        ) {
            state.observe(owner, observer)
        }

        override fun showProgress() {
            state.value = State(signUpInProgress = true)
        }

        override fun hideProgress() {
            state.value = state.value?.copy(signUpInProgress = false)
        }

        private fun handleInvalidException() {
            state.value = state.value?.copy(phoneErrorMessageRes = R.string.generic_exception)
        }

        private fun handleNoInternetException(){
            state.value = state.value?.copy(phoneErrorMessageRes = R.string.no_internet_exception)
        }

        private fun handleRecaptchaException(){
            state.value = state.value?.copy(phoneErrorMessageRes = R.string.recaptcha_exception)
        }

        private fun handleEmptyFieldError(e: EmptyFieldException) {
            state.value = when (e.field) {
                Field.PHONE -> state.value
                    ?.copy(phoneErrorMessageRes = R.string.field_is_empty)
                Field.NAME -> state.value
                    ?.copy(usernameErrorMessageRes = R.string.field_is_empty)
                Field.CODE -> state.value
                    ?.copy(codeErrorMessageRes = R.string.field_is_empty)
            }
            hideProgress()
        }
    }

}