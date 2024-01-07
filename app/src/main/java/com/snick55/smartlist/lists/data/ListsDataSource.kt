package com.snick55.smartlist.lists.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.log
import com.snick55.smartlist.profile.domain.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ListsDataSource {
    fun getAllLists(): Flow<List<ListItemData>>

    class ListsDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
    ) : ListsDataSource {

        private val sharedFlow = MutableStateFlow<List<ListItemData>>(emptyList())


        init {
            val acc = firebaseProvider.provideAuth().currentUser
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list: MutableList<ListItemData> = mutableListOf()
                    if (acc == null) return
                    snapshot.child("newList").child(acc.uid).children.forEach {
                        val id =it.key.toString()
                        val name =it.child("name").value.toString()
                        val date =it.child("date").value.toString()
                        list.add(ListItemData(id, name, date))
                    }
                    sharedFlow.tryEmit(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    sharedFlow.tryEmit(emptyList())
                }
            })

        }

        override fun getAllLists(): Flow<List<ListItemData>> =  sharedFlow
    }

}