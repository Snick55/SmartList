package com.snick55.smartlist.lists.domain

import com.snick55.smartlist.core.Container
import kotlinx.coroutines.flow.Flow

interface ListsRepository {

    fun getAllLists(): Flow<Container<List<ListItemDomain>>>

    suspend fun createNewList(listName: String)

}