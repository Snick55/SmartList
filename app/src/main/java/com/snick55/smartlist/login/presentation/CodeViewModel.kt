package com.snick55.smartlist.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.R
import com.snick55.smartlist.core.log
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.login.domain.PhoneUseCase
import com.snick55.smartlist.login.domain.SignInUseCase
import com.snick55.smartlist.login.domain.entities.SignInWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CodeViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val useCase: SignInUseCase,
    private val phoneUseCase: PhoneUseCase
): ViewModel() {

    private val _state = MutableLiveData(State())
    val state: LiveData<State> = _state

    fun signInWithCode(code:String,verificationId: String) = viewModelScope.launch(ioDispatcher) {
        try {
          useCase.execute(SignInWrapper(code,verificationId))
            phoneUseCase.execute()
            withContext(mainDispatcher){
                _state.value = State(token = verificationId)
            }
        }catch (e: java.lang.Exception){
            log("error is $e")
            _state.postValue(_state.value?.copy(codeErrorMessageRes = R.string.invalid_code))
        }
    }

}