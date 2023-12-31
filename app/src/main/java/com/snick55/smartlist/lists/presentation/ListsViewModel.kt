package com.snick55.smartlist.lists.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.core.log
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.lists.domain.GetAllListsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val getAllListsUseCase: GetAllListsUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
) : ViewModel() {


    private val lists: Flow<List<ListItemUi>> = getAllListsUseCase.execute().map {listsDomain->
        listsDomain.map {
            ListItemUi(it.id,it.name,it.date)
        }
    }
        .flowOn(ioDispatcher)
        .shareIn(viewModelScope, SharingStarted.Lazily)

    fun setData(adapter: ListsAdapter) {
        viewModelScope.launch(mainDispatcher) {
            lists.collectLatest {
                adapter.submitList(it)
            }
        }
    }
}