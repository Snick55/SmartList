package com.snick55.smartlist.lists.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.log
import com.snick55.smartlist.login.domain.GenericException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import javax.inject.Inject


interface ListsDataSource {
    fun getAllLists(): Flow<Container<List<ListItemData>>>

    suspend fun createList(listName: String)
    class ListsDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
    ) : ListsDataSource {

        private val sharedFlow = MutableStateFlow<Container <List<ListItemData>>>(Container.Success(
            emptyList())
        )


        init {
            val acc = firebaseProvider.provideAuth().currentUser
                firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val list: MutableList<ListItemData> = mutableListOf()
                        if (acc == null) return
                        snapshot.child("lists").child(acc.uid).children.forEach {
                            val id =it.key.toString()
                            val name =it.child("name").value.toString()
                            val date = it.child("date").value ?: return
                            list.add(ListItemData(id, name, date.toString()))
                        }
                        sharedFlow.tryEmit(Container.Success(list))
                    }

                    override fun onCancelled(error: DatabaseError) {

                        Container.Error(GenericException())
                    }
                })
        }

        override suspend fun createList(listName: String) {
            val userId = firebaseProvider.provideAuth().currentUser!!.uid
            val uuid = UUID.randomUUID()
            firebaseDatabaseProvider.provideDBRef().child("lists").child(userId).child("$uuid").child("name").setValue(listName)
            firebaseDatabaseProvider.provideDBRef().child("lists").child(userId).child("$uuid").child("members").child(userId).setValue(userId)
            firebaseDatabaseProvider.provideDBRef().child("lists").child(userId).child("$uuid").child("date").setValue("${System.currentTimeMillis()}")
        }

        override fun getAllLists(): Flow<Container<List<ListItemData>>> =  sharedFlow
    }

}