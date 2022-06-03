package com.parita.paginationapp

import android.net.Uri
import android.util.Log
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.parita.paginationapp.network.ResultsItem
import com.parita.paginationapp.network.RetroInstance
import com.parita.paginationapp.network.RetroService
import com.parita.paginationapp.network.RickMortyApiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharacterPagingSource(val apiService: RetroService) : PagingSource<Int, ResultsItem>() {
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getDataFromAPI(nextPage)
            var nextPageNumber: Int? = null
            if (response?.info?.next != null) {
                val uri = Uri.parse(response?.info?.next!!)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            LoadResult.Page(data = response.results, prevKey = null, nextKey = nextPageNumber)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}