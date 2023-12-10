package com.snick55.smartlist.login.presentation

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.LiveContainer
import com.snick55.smartlist.core.MutableLiveContainer
import com.snick55.smartlist.core.log
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.login.domain.ErrorHandler
import com.snick55.smartlist.login.domain.GetCodeByNumberUseCase
import com.snick55.smartlist.login.domain.entities.PhoneRequestWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCase: GetCodeByNumberUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val communication: LoginStateCommunication,
    private val errorHandler: ErrorHandler
) : ViewModel() {



    fun getCode( phoneNumber: String, activity: FragmentActivity) = viewModelScope.launch(ioDispatcher) {
        withContext(mainDispatcher){
            communication.showProgress()
        }
        try {
            useCase.execute(PhoneRequestWrapper(phoneNumber, activity,callback))
        }catch (e:java.lang.Exception){
            withContext(mainDispatcher){
                log("error is $e")
                communication.map(errorHandler.handle(e))
            }
        }
    }

    fun observeState(owner: LifecycleOwner, observer: Observer<State>){
        communication.observe(owner, observer)
    }

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            Log.d("TAG", "onVerificationCompleted:$credential")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w("TAG", "onVerificationFailed", e)
            communication.map(errorHandler.handle(e))

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d("TAG", "onCodeSent:$verificationId + $token")
            communication.map(verificationId)
        }
    }
}