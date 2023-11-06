package com.snick55.smartlist.login.domain


import com.snick55.smartlist.login.domain.entities.PhoneRequestWrapper
import javax.inject.Inject

interface GetCodeByNumberUseCase {

  suspend  fun execute(phoneRequestWrapper: PhoneRequestWrapper)


    class GetCodeByNumberUseCaseImpl @Inject constructor(
        private val repository: LoginRepository
    ): GetCodeByNumberUseCase{
        override suspend fun execute(phoneRequestWrapper: PhoneRequestWrapper) {
                repository.getCode(phoneRequestWrapper)
        }
    }
}