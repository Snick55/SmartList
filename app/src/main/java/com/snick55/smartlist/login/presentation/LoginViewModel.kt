package com.snick55.smartlist.login.presentation

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.LiveContainer
import com.snick55.smartlist.core.MutableLiveContainer
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
    private val errorHandler: ErrorHandler
) : ViewModel() {



    private val _state = MutableLiveContainer<String>()
    val state: LiveContainer<String> = _state

    fun getCode(phoneNumber: String, activity: FragmentActivity) = viewModelScope.launch(ioDispatcher) {
        _state.postValue(Container.Pending)
        try {
            useCase.execute(PhoneRequestWrapper(phoneNumber, activity,callback))
        }catch (e:java.lang.Exception){
            withContext(mainDispatcher){
                _state.value = Container.Error(errorHandler.handle(e))
            }
        }
    }
    private lateinit var tok: PhoneAuthProvider.ForceResendingToken

    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            Log.d("TAG", "onVerificationCompleted:$credential")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w("TAG", "onVerificationFailed", e)
            _state.value = Container.Error(errorHandler.handle(e))

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            Log.d("TAG", "onCodeSent:$verificationId + $token")
            _state.value = Container.Success(verificationId)
            // resendToken = token
            tok = token
        }
    }
}