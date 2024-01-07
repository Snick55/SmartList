package com.snick55.smartlist.lists.data

import com.snick55.smartlist.lists.domain.ListItemDomain
import com.snick55.smartlist.lists.domain.ListsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ListsRepositoryImpl @Inject constructor(
    private val listsDataSource: ListsDataSource
): ListsRepository {

    override fun getAllLists(): Flow<List<ListItemDomain>> {
        return listsDataSource.getAllLists().map {lists->
            lists.map {
                it.toDomain()
            }
        }

    }
}