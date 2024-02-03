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
                        dateFrom = try {
                            dateFormater.format(it.dateFrom.toLong())
                        } catch (e: Exception) {
                            it.dateFrom
                        },
                        dateTo = try {
                            dateFormater.format(it.dateTo.toLong())
                        } catch (e: Exception) {
                            it.dateTo
                        }
                    )
                }
            }
        }
    }


}