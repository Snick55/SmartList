package com.snick55.smartlist.login.data

import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.snick55.smartlist.core.FirebaseProvider
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

interface LoginDataSource {

    suspend fun getCodeByNumber(phoneNumber: String, callbackActivity: FragmentActivity, phoneCallback: PhoneAuthProvider.OnVerificationStateChangedCallbacks)

    suspend fun signIn(code: String, verificationId: String)

    class FireBaseLoginSource @Inject constructor(
        private val firebaseProvider: FirebaseProvider
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

        private suspend fun authResult(pending: Task<AuthResult>) =
            suspendCoroutine<AuthResult> { continuation ->
                pending
                    .addOnSuccessListener { continuation.resume(it) }
                    .addOnFailureListener { continuation.resumeWithException(it) }
            }
    }
}