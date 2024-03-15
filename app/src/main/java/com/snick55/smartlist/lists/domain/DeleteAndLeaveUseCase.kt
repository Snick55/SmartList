package com.snick55.smartlist.lists.domain

import javax.inject.Inject

interface DeleteAndLeaveUseCase {
  suspend  fun execute(listId: String)
    class DeleteAndLeaveUseCaseImpl @Inject constructor(
        private val repository: ListsRepository
    ) : DeleteAndLeaveUseCase {
        override suspend fun execute(listId: String) {
            repository.leaveAndDelete(listId)
        }
    }
}