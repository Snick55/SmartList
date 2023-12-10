package com.snick55.smartlist.profile.domain

import com.snick55.smartlist.login.domain.LoginRepository
import javax.inject.Inject

interface LogoutUseCase {

    suspend fun execute()

    class LogoutUseCaseImpl @Inject constructor (private val repository: ProfileRepository): LogoutUseCase{

        override suspend fun execute() {
            repository.logout()
        }
    }

}