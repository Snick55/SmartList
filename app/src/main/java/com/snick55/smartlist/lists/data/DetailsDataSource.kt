package com.snick55.smartlist.lists.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.log
import com.snick55.smartlist.members.data.MemberData
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.shareIn
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

interface DetailsDataSource {

    fun getAllItems(listId: String): Flow<List<DetailsItemData>>
    suspend fun createProduct(name: String, count: String, dateFrom: String, dateTo: String)

    fun getAllMembersInList(): Flow<List<MemberData>>

    fun getAllMembers(): Flow<List<MemberData>>

    suspend fun addMemberByUUID(UUID: String)
    suspend fun deleteItem(id: String)

    @Singleton
    class DetailsDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
    ) : DetailsDataSource {

        private val sharedFlowDetails = MutableSharedFlow<List<DetailsItemData>>(
            replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
        )

        private val sharedFlowMembersInList = MutableSharedFlow<List<MemberData>>(
            replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
        )

        private val sharedFlowMembers = MutableSharedFlow<List<MemberData>>(
            replay = 1, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_LATEST
        )

        private var currentListId = ""

        override fun getAllItems(listId: String): Flow<List<DetailsItemData>> {
            val currentUser = firebaseProvider.provideAuth().currentUser
            currentListId = listId
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list: MutableList<DetailsItemData> = mutableListOf()
                    if (currentUser == null) return
                    snapshot.child("allLists").child(listId).child("details")
                        .child("items").children.forEach {
                            val id = it.key.toString()
                            val name = it.child("name").value.toString()
                            val dateFrom = it.child("dateFrom").value ?: return
                            val dateTo = it.child("dateTo").value ?: return
                            val count = it.child("count").value.toString()
                            val isDone = it.child("isDone").value ?: false
                            val itemData = DetailsItemData(
                                id,
                                name,
                                count,
                                dateFrom.toString(),
                                dateTo.toString(),
                                isDone as Boolean
                            )
                            list.add(
                                itemData
                            )
                        }
                    sharedFlowDetails.tryEmit(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    log("DetailsDataSource exception $error")
                }
            })
            return sharedFlowDetails
        }

        override suspend fun createProduct(
            name: String,
            count: String,
            dateFrom: String,
            dateTo: String
        ) {
            val productId = "$name+${UUID.randomUUID()}"
            val ref = firebaseDatabaseProvider.provideDBRef().child("allLists")
                .child(currentListId)
                .child("details").child("items").child(productId)

            ref.child("name").setValue(name)
            ref.child("count").setValue(count)
            ref.child("dateFrom").setValue(dateFrom)
            ref.child("dateTo").setValue(dateTo)
            ref.child("isDone").setValue(false)

        }

        override fun getAllMembersInList(): Flow<List<MemberData>> {
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val membersList = mutableListOf<MemberData>()
                    snapshot.child("allLists")
                        .child(currentListId).child("members").children.forEach {
                            val userId = it.key ?: return
                            val name =
                                snapshot.child("users").child(userId).child("name").value ?: ""
                            val phone =
                                snapshot.child("users").child(userId).child("phone").value ?: ""
                            membersList.add(MemberData(name as String, phone as String, userId))
                        }
                    sharedFlowMembersInList.tryEmit(membersList)
                }

                override fun onCancelled(error: DatabaseError) {
                    log("getAllMembers error = $error")
                }

            })
            return sharedFlowMembersInList
        }

        override suspend fun deleteItem(id: String) {
            firebaseDatabaseProvider.provideDBRef().child("allLists").child(currentListId).child("details")
                .child("items").child(id).setValue(null)
        }

        override fun getAllMembers(): Flow<List<MemberData>> {
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val membersList = mutableListOf<MemberData>()
                    snapshot.child("usersByPhone").children.forEach {
                        val phone = it.key ?: return
                        val name = it.child("name").value ?: ""
                        val userId = it.child("userID").value ?: ""
                        val member = MemberData(name as String, phone, userId as String)
                        membersList.add(member)
                    }
                    sharedFlowMembers.tryEmit(membersList)
                }

                override fun onCancelled(error: DatabaseError) {
                    log("getAllMembers error = $error")
                }

            })
            return sharedFlowMembers
        }


        override suspend fun addMemberByUUID(UUID: String) {
            firebaseDatabaseProvider.provideDBRef().child("allLists").child(currentListId)
                .child("members").child(UUID).setValue("member")
        }
    }

}