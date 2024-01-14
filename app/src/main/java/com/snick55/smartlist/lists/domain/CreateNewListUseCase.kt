package com.snick55.smartlist.lists.domain

import javax.inject.Inject

interface CreateNewListUseCase {

   suspend fun execute(listName: String)


    class CreateNewListUseCaseImpl @Inject constructor(
        private val repository: ListsRepository
    ): CreateNewListUseCase{
        override suspend fun execute(listName: String) {
            if (listName.isBlank()) return
            repository.createNewList(listName)
        }
    }

}