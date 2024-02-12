package com.snick55.smartlist.login.data

import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.PreferenceSource
import java.lang.IllegalStateException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface LoginDataSource {

    suspend fun initPhone()

    suspend fun getCodeByNumber(phoneNumber: String, callbackActivity: FragmentActivity, phoneCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks)

    suspend fun signIn(code: String, verificationId: String)

    class FireBaseLoginSource @Inject constructor(
        private val firebaseProvider: FirebaseProvider,
        private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
        private val preferenceSource: PreferenceSource<String>
    ) : LoginDataSource {

        override suspend fun getCodeByNumber(
            phoneNumber: String,
            callbackActivity: FragmentActivity,
            phoneCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
        ) {
            val options = PhoneAuthOptions.newBuilder(firebaseProvider.provideAuth())
                .setPhoneNumber(phoneNumber) // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(callbackActivity) // Activity (for callback binding)
                .setCallbacks(phoneCallback) // OnVerificationStateChangedCallbacks
                .build()
             PhoneAuthProvider.verifyPhoneNumber(options)
        }

        override suspend fun signIn(code: String, verificationId: String) {
            val credentials = PhoneAuthProvider.getCredential(verificationId,code)
            authResult(firebaseProvider.provideAuth().signInWithCredential(credentials))
        }


        override suspend fun initPhone() {
            val currentUser = firebaseProvider.provideAuth().currentUser ?: throw IllegalStateException("current user cant be null")
            firebaseDatabaseProvider.provideDBRef().child("users").child(currentUser.uid).child("phone").setValue(currentUser.phoneNumber)
            firebaseDatabaseProvider.provideDBRef().child("usersByPhone").child("${currentUser.phoneNumber}").child("name").setValue(preferenceSource.getValue())
            firebaseDatabaseProvider.provideDBRef().child("usersByPhone").child("${currentUser.phoneNumber}").child("userID").setValue(currentUser.uid)

        }

        private suspend fun authResult(pending: Task<AuthResult>) =
            suspendCoroutine<AuthResult> { continuation ->
                pending
                    .addOnSuccessListener { continuation.resume(it) }
                    .addOnFailureListener { continuation.resumeWithException(it) }
            }
    }
}