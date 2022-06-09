package com.parita.paginationapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.parita.paginationapp.network.ResultsItem
import com.parita.paginationapp.network.RetroInstance
import com.parita.paginationapp.network.RetroService
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    var retroService : RetroService

    init {
        retroService = RetroInstance.getRetrofitInstance().create(RetroService::class.java)
    }
    val response : Flow<PagingData<ResultsItem>> = Pager(config=PagingConfig(34, 200), pagingSourceFactory = {
        CharacterPagingSource(retroService)
    }).flow.cachedIn(viewModelScope)
}