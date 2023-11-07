package com.snick55.smartlist.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.core.log
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseProvider: FirebaseProvider,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _launchState = MutableLiveData<Boolean>()
    val launchState: LiveData<Boolean> = _launchState

    init {
        isAuthorize()
    }

   private fun isAuthorize() = viewModelScope.launch(ioDispatcher) {
        delay(1000)
        val isAuthorize = firebaseProvider.isAuthorize()
        withContext(mainDispatcher) {
            _launchState.value = isAuthorize
        }
    }

}