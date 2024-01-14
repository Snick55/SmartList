package com.snick55.smartlist.lists.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.lists.domain.CreateNewListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddListViewModel @Inject constructor(
    private val firebaseProvider: FirebaseProvider,
    private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
    private val createNewListUseCase: CreateNewListUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    fun createList(name:String)= viewModelScope.launch(ioDispatcher){
        createNewListUseCase.execute(name)
    }

}