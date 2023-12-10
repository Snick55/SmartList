package com.snick55.smartlist.login.domain.entities

import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.PhoneAuthProvider

data class PhoneRequestWrapper(
    val phoneNumber: String,
    val activity: FragmentActivity,
    val callback: PhoneAuthProvider.OnVerificationStateChangedCallbacks
)
