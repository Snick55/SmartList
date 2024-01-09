package com.snick55.smartlist.lists.domain

import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.log
import com.snick55.smartlist.login.domain.EmptyFieldException
import com.snick55.smartlist.login.domain.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetAllListsUseCase {

    fun execute(): Flow<Container<List<ListItemDomain>>>

    class GetAllListsUseCaseImpl @Inject constructor(
        private val repository: ListsRepository
    ) : GetAllListsUseCase {

        override fun execute(): Flow<Container<List<ListItemDomain>>> = flow {
            repository.getAllLists().catch {
                emit(Container.Error(it))
            }.collect {
                if (it.unwrap().isEmpty()) {
                    emit(Container.Error(EmptyListsException()))
                }
                else
                    emit(it)
            }
        }
    }
}