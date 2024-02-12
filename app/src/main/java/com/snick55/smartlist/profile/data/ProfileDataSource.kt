package com.snick55.smartlist.profile.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.PreferenceSource
import com.snick55.smartlist.profile.domain.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

interface ProfileDataSource {

    suspend fun logout()

    suspend fun getProfileInfo(): Flow<Account>

    suspend fun changeName(name: String)


    class ProfileDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
        private val preferenceSource: PreferenceSource<String>
    ) : ProfileDataSource {


        private val sharedFlow = MutableStateFlow(Account("", ""))
        private var acc: FirebaseUser? = null

        init {
            acc = firebaseProvider.provideAuth().currentUser
            firebaseDatabaseProvider.provideDBRef().addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (acc == null) return
                    val name = (snapshot.child("users").child(acc!!.uid).child("name").value
                        ?: "") as String
                    val phone = (snapshot.child("users").child(acc!!.uid).child("phone").value
                        ?: "") as String
                    sharedFlow.tryEmit(Account(name, "${acc!!.phoneNumber}"))
                }

                override fun onCancelled(error: DatabaseError) {
                    sharedFlow.tryEmit(Account("$error", ""))
                }
            })
        }

        override suspend fun changeName(name: String) {
            delay(1000)
            if (acc == null) return
            preferenceSource.putValue(name)
            firebaseDatabaseProvider.provideDBRef().child("users").child(acc!!.uid).child("name")
                .setValue(name)
        }

        override suspend fun logout() {
            firebaseProvider.provideAuth().signOut()
            acc = null
        }


        override suspend fun getProfileInfo(): Flow<Account> {
            return sharedFlow.asSharedFlow()
        }
    }

}