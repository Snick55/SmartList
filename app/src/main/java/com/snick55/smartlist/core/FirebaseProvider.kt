package com.snick55.smartlist.core

import com.google.firebase.auth.FirebaseAuth

import javax.inject.Inject

interface FirebaseProvider {

    fun provideAuth(): FirebaseAuth

   suspend fun isAuthorize(): Boolean

    class FirebaseProviderImpl @Inject constructor(): FirebaseProvider {

        private var firebaseAuth: FirebaseAuth? = null

        init {
            firebaseAuth = FirebaseAuth.getInstance().also {
                it.setLanguageCode("ru")
            }
        }

        override fun provideAuth(): FirebaseAuth {
            return firebaseAuth!!
        }

        override suspend fun isAuthorize(): Boolean {
            return firebaseAuth!!.currentUser != null
        }
    }

}