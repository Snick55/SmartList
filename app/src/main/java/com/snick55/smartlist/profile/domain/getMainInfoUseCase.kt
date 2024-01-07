package com.snick55.smartlist.profile.domain

import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


interface GetMainInfoUseCase {

    fun execute(): Flow<Container<Account>>


    class GetMainInfoUseCaseImpl @Inject constructor(private val repository: ProfileRepository): GetMainInfoUseCase{
        override fun execute(): Flow<Container<Account>> = flow {
            repository.getMainInfoFlow().catch {
                emit(Container.Error(it))
            }.collect {
                emit(Container.Success(it.copy(name = it.name, phone = it.phone.replace("+7","8"))))
            }
        }
    }
}