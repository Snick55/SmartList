package com.snick55.smartlist.lists.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.login.domain.GenericException
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import java.util.UUID
import javax.inject.Inject


interface ListsDataSource {
    fun getAllLists(): Flow<Container<List<ListItemData>>>
    suspend fun leaveAndDelete(listId: String)

    suspend fun createList(listName: String)
    class ListsDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
    ) : ListsDataSource {

        private val sharedFlow = MutableSharedFlow<Container<List<ListItemData>>>(
            replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
        )


        init {
            val acc = firebaseProvider.provideAuth().currentUser
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list: MutableList<ListItemData> = mutableListOf()
                    if (acc == null) return

                    snapshot.child("allLists").children.forEach {
                        val membersList = mutableListOf<String>()
                        val id = it.key.toString()
                        val name = it.child("name").value.toString()
                        it.child("members").children.forEach { members ->
                            membersList.add((members.key) as String)
                        }
                        val date = it.child("date").value ?: return
                        list.add(ListItemData(id, name, date.toString(), membersList))
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
            firebaseDatabaseProvider.provideDBRef().child("allLists").child("$uuid").child("name")
                .setValue(listName)
            firebaseDatabaseProvider.provideDBRef().child("allLists").child("$uuid")
                .child("members").child(userId).setValue("creator")
            firebaseDatabaseProvider.provideDBRef().child("allLists").child("$uuid").child("date")
                .setValue("${System.currentTimeMillis()}")


        }

        override suspend fun leaveAndDelete(listId: String) {
            firebaseDatabaseProvider.provideDBRef().child("allLists").child(listId).child("members")
                .child(firebaseProvider.provideAuth().currentUser!!.uid).setValue(null)
        }

        override fun getAllLists(): Flow<Container<List<ListItemData>>> = flow {
            sharedFlow.collect {
                emit(Container.Success(it.unwrap().filter { item ->
                    item.members.contains("${firebaseProvider.provideAuth().uid}")
                }))
            }
        }
    }

}