package com.parita.paginationapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.parita.paginationapp.network.ResultsItem
import com.parita.paginationapp.network.RetroInstance
import com.parita.paginationapp.network.RetroService
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainViewModel : ViewModel() {
    lateinit var retroService : RetroService

    init {
        retroService = RetroInstance.getRetrofitInstance().create(RetroService::class.java)
    }
    fun getRecyclerListData() : Flow<PagingData<ResultsItem>> {
        return Pager(config = PagingConfig(pageSize = 34, maxSize = 200), pagingSourceFactory = {
            CharacterPagingSource(retroService)}).flow.cachedIn(viewModelScope)
    }
}