package com.snick55.smartlist.members.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.members.domain.AddMemberToListUseCase
import com.snick55.smartlist.members.domain.GetAllUsersByPhoneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMembersViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getAllUsersByPhoneUseCase: GetAllUsersByPhoneUseCase,
    private val addMemberToListUseCase: AddMemberToListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

   private var job: Job? = null


    fun getUsersByPhone(phoneNumber: String) {
        job?.cancel()
        _state.value = State.Loading
        job = viewModelScope.launch(ioDispatcher) {
            getAllUsersByPhoneUseCase.execute(phoneNumber)
                .catch {
                    _state.postValue(State.Failure(it.message ?: ""))
                }
                .collectLatest { users ->
                    _state.postValue(State.Success(users.map { MemberUi(it.name, it.phoneNumber,it.UUID) }))
                }
        }
    }

    fun addMember(member: MemberUi) = viewModelScope.launch(ioDispatcher){
        addMemberToListUseCase.execute(member.UUID)
    }

    sealed class State {

        data class Success(val users: List<MemberUi>) : State()

        data class Failure(val error: String) : State()

        object Loading : State()

    }
}