package com.snick55.smartlist.lists.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import com.snick55.smartlist.lists.domain.DeleteItemUseCase
import com.snick55.smartlist.lists.domain.GetAllItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListDetailsViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val getAllItemsUseCase: GetAllItemsUseCase,
    private val deleteItemUseCase: DeleteItemUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<ListItemDetails>>(emptyList())
    val items: StateFlow<List<ListItemDetails>> = _items
    fun getDetails(listId: String) = viewModelScope.launch(ioDispatcher) {
        getAllItemsUseCase.execute(listId).map { listDomain ->
            listDomain.map {
                ListItemDetails(it.id, it.name, it.count, it.dateFrom, it.dateTo, it.isDone)
            }
        }
            .catch {
            }
            .collect {
                _items.value = it
            }
    }

    fun deleteItem(id: String)= viewModelScope.launch(ioDispatcher) {
        deleteItemUseCase.execute(id)
    }
}