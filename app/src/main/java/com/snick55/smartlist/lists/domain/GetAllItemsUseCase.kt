package com.snick55.smartlist.lists.domain

import com.snick55.smartlist.core.DateFormater
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetAllItemsUseCase {

    fun execute(listId: String): Flow<List<DetailsItemDomain>>


    class GetAllItemsUseCaseImpl @Inject constructor(
        private val repository: DetailsRepository,
    ) : GetAllItemsUseCase {

        override fun execute(listId: String): Flow<List<DetailsItemDomain>> {
            return repository.getAllItems(listId)
        }
    }


}