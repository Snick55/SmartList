package com.snick55.smartlist.lists.domain

import com.snick55.smartlist.login.domain.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllListsUseCase {

    fun execute(): Flow<List<ListItemDomain>>

    class GetAllListsUseCaseImpl @Inject constructor(
       private val repository: ListsRepository
    ): GetAllListsUseCase{

        override fun execute(): Flow<List<ListItemDomain>> {
           return repository.getAllLists()
        }
    }
}