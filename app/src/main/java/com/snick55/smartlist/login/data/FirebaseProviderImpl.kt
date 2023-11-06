package com.snick55.smartlist.login.data

import com.google.firebase.auth.FirebaseAuth

import javax.inject.Inject

interface FirebaseProvider {

    fun provideAuth(): FirebaseAuth

    class FirebaseProviderImpl @Inject constructor(): com.snick55.smartlist.login.data.FirebaseProvider{

        private var firebaseAuth: FirebaseAuth? = null

        init {
            firebaseAuth = FirebaseAuth.getInstance().also {
                it.setLanguageCode("ru")
            }
        }

        override fun provideAuth(): FirebaseAuth {
            return firebaseAuth!!
        }
    }

}