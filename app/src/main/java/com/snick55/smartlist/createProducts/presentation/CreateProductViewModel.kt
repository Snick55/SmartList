package com.snick55.smartlist.createProducts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snick55.smartlist.createProducts.domain.CreateProductUseCase
import com.snick55.smartlist.di.IoDispatcher
import com.snick55.smartlist.di.MainDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateProductViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val createProductUseCase: CreateProductUseCase
) : ViewModel() {

    fun createProduct(name: String,count:String?,dateFrom:String?,dateTo:String?) = viewModelScope.launch(ioDispatcher){
        createProductUseCase.execute(name,count,dateFrom,dateTo)
    }
}