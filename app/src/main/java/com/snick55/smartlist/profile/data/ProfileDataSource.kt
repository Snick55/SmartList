package com.snick55.smartlist.profile.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.log
import com.snick55.smartlist.profile.domain.Account
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface ProfileDataSource {

    suspend fun logout()

    suspend fun getProfileInfo(): Flow<Account>

    suspend fun changeName(name: String)


    class ProfileDataSourceImpl @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider
    ) : ProfileDataSource {

        private var dbREf: DatabaseReference? = null
        private val sharedFlow = MutableStateFlow(Account("", ""))

        private var acc: FirebaseUser? = null

        init {
            dbREf = firebaseDatabaseProvider.provideDBRef()
            acc = firebaseProvider.provideAuth().currentUser
            dbREf!!.addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (acc == null) return
                    val name = (snapshot.child("users").child(acc!!.uid).child("name").value
                        ?: "") as String
                    val phone = (snapshot.child("users").child(acc!!.uid).child("phone").value
                        ?: "") as String
                    sharedFlow.tryEmit(Account(name, phone))
                }

                override fun onCancelled(error: DatabaseError) {
                    log("$error")
                }
            })

        }

        override suspend fun changeName(name: String) {
            if (acc == null) return
            dbREf!!.child("users").child(acc!!.uid).child("name").setValue(name)
        }

        override suspend fun logout() {
            firebaseProvider.provideAuth().signOut()
            acc = null
            dbREf = null
        }


        override suspend fun getProfileInfo(): Flow<Account> {
            return sharedFlow.asSharedFlow()
        }
    }

}