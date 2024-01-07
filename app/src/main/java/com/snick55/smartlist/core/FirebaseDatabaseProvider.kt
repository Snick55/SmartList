package com.snick55.smartlist.core

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject

interface FirebaseDatabaseProvider {


    fun provideDBRef(): DatabaseReference



    class FirebaseDatabaseProviderImpl @Inject constructor(): FirebaseDatabaseProvider{

        private var dBRef: DatabaseReference? = null

        init {
            dBRef = Firebase.database.reference
        }

        override fun provideDBRef(): DatabaseReference {
            return dBRef!!
        }
    }
}