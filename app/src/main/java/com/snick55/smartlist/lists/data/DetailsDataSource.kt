package com.snick55.smartlist.lists.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

interface DetailsDataSource {

    fun getAllItems(listId:String): Flow<List<DetailsItemData>>


    class DetailsDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
    ): DetailsDataSource{

        private val sharedFlow = MutableStateFlow<List<DetailsItemData>>(
            emptyList()
        )

        override fun getAllItems(listId: String): Flow<List<DetailsItemData>> {
            val currentUser = firebaseProvider.provideAuth().currentUser
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list: MutableList<DetailsItemData> = mutableListOf()
                    if (currentUser == null) return
                    snapshot.child("lists").child(currentUser.uid).child(listId).child("details").child("items").children.forEach {
                        val id = it.key.toString()
                        val name =it.child("name").value.toString()
                        val dateFrom = it.child("dateFrom").value ?: return
                        val dateTo = it.child("dateTo").value ?: return
                        val count = it.child("count").value.toString()
                        val isDone = it.child("isDone").value ?: false
                        list.add(DetailsItemData(id, name,count,dateFrom.toString(),dateTo.toString(),isDone as Boolean))
                    }
                    sharedFlow.tryEmit(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    log("DetailsDataSource exception $error")
                }
            })
            return sharedFlow
        }
    }

}