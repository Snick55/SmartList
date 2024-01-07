package com.snick55.smartlist.login.domain

import javax.inject.Inject

interface PhoneUseCase {

    suspend fun execute()

    class PhoneUseCaseImpl @Inject constructor(
      private val repository: LoginRepository
    ): PhoneUseCase{

        override suspend fun execute() {
            repository.initPhone()
        }
    }
}