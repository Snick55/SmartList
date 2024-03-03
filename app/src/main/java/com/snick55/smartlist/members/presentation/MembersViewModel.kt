package com.snick55.smartlist.members.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.members.domain.GetAllMembersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getAllMembersUseCase: GetAllMembersUseCase
) : ViewModel() {

    private val _members = MutableLiveData<List<MemberUi>>()
    val members: LiveData<List<MemberUi>> = _members
    fun loadMembers() = viewModelScope.launch(ioDispatcher) {
        getAllMembersUseCase.execute().collectLatest {
            withContext(mainDispatcher) {
                _members.value = it.map {
                    MemberUi(it.name, it.phoneNumber,it.UUID)
                }
            }
        }
    }
}