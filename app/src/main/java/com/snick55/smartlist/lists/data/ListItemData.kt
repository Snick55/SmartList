package com.snick55.smartlist.lists.data

import com.snick55.smartlist.lists.domain.ListItemDomain

data class ListItemData(val id: String, val name: String,val date: String){
   fun toDomain() = ListItemDomain(id, name, date)
}
