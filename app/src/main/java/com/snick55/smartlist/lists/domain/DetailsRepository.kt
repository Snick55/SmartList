package com.snick55.smartlist.lists.domain

import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun getAllItems(listId:String): Flow<List<DetailsItemDomain>>
    suspend fun deleteItem(id: String)

}