package com.snick55.smartlist.lists.domain

import com.snick55.smartlist.core.DateFormater
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetAllItemsUseCase {

    fun execute(listId: String): Flow<List<DetailsItemDomain>>


    class GetAllItemsUseCaseImpl @Inject constructor(
        private val repository: DetailsRepository,
        private val dateFormater: DateFormater
    ) : GetAllItemsUseCase {

        override fun execute(listId: String): Flow<List<DetailsItemDomain>> {
            return repository.getAllItems(listId).map { listDomain ->
                listDomain.map {
                    it.copy(
                        dateFrom = dateFormater.format(it.dateFrom.toLong()),
                        dateTo = dateFormater.format(it.dateTo.toLong())
                    )
                }
            }
        }
    }


}