package com.snick55.smartlist.login.presentation

import androidx.annotation.StringRes

data class State(
    @StringRes val phoneErrorMessageRes: Int = NO_ERROR_MESSAGE,
    @StringRes val usernameErrorMessageRes: Int = NO_ERROR_MESSAGE,
    @StringRes val codeErrorMessageRes: Int = NO_ERROR_MESSAGE,
    private val signUpInProgress: Boolean = false,
    val token: String = ""
) {
    val canGo = token.isNotBlank()
    //val canGo = phoneErrorMessageRes == NO_ERROR_MESSAGE && usernameErrorMessageRes == NO_ERROR_MESSAGE && codeErrorMessageRes == NO_ERROR_MESSAGE
    val showProgress: Boolean get() = signUpInProgress
    val enableViews: Boolean get() = !signUpInProgress
    companion object {
        const val NO_ERROR_MESSAGE = 0
    }
}


