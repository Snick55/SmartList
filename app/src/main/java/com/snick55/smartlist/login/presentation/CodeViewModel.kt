package com.snick55.smartlist.login.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
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
    private val useCase: SignInUseCase
): ViewModel() {

    private val _state = MediatorLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun signInWithCode(code:String,verificationId: String) = viewModelScope.launch(ioDispatcher) {
        try {
            useCase.execute(SignInWrapper(code,verificationId))
            withContext(mainDispatcher){
                _state.value = true
            }
        }catch (e: java.lang.Exception){
            _state.postValue(false)
        }


    }

}