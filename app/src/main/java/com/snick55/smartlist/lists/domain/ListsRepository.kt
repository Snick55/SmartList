package com.snick55.smartlist.lists.domain

import kotlinx.coroutines.flow.Flow

interface ListsRepository {

    fun getAllLists():Flow<List<ListItemDomain>>

}