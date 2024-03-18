package com.snick55.smartlist.lists.domain

import javax.inject.Inject

interface DeleteItemUseCase {

   suspend fun execute(id: String)


    class DeleteItemUseCaseImpl @Inject constructor(
        private val repository: DetailsRepository
    ): DeleteItemUseCase{
        override suspend fun execute(id: String) {
            repository.deleteItem(id)
        }
    }

}