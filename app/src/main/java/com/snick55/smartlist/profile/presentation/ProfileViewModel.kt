package com.snick55.smartlist.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.core.Container
import com.snick55.smartlist.core.LiveContainer
import com.snick55.smartlist.core.MutableLiveContainer
import com.snick55.smartlist.core.log
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.profile.domain.Account
import com.snick55.smartlist.profile.domain.ChangeNameUseCase
import com.snick55.smartlist.profile.domain.GetMainInfoUseCase
import com.snick55.smartlist.profile.domain.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val logoutUseCase: LogoutUseCase,
    private val getMainInfoUseCase: GetMainInfoUseCase,
    private val changeNameUseCase: ChangeNameUseCase
): ViewModel() {

    private val _account = MutableLiveContainer<Account>()
    val account:LiveContainer<Account> = _account

    init {
        viewModelScope.launch(ioDispatcher) {
            getMainInfoUseCase.execute().collect{
                _account.postValue(it)
            }
        }
    }

    fun logout()= viewModelScope.launch(ioDispatcher){
        logoutUseCase.execute()
    }

    fun changeName(name: String) = viewModelScope.launch(ioDispatcher){
        withContext(mainDispatcher) {
            _account.value = Container.Pending
        }
        changeNameUseCase.execute(name)
    }


}