package com.snick55.smartlist.lists.data

import com.snick55.smartlist.lists.domain.DetailsItemDomain
import com.snick55.smartlist.lists.domain.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val detailsDataSource: DetailsDataSource
): DetailsRepository {

    override suspend fun deleteItem(id: String) {
        detailsDataSource.deleteItem(id)
    }

    override fun getAllItems(listId:String): Flow<List<DetailsItemDomain>> {
        return detailsDataSource.getAllItems(listId).map {listData->
            listData.map {
                DetailsItemDomain(it.id,it.name,it.count,it.dateFrom,it.dateTo,it.isDone)
            }
        }
    }
}