package com.parita.paginationapp.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {
    @GET("character")
    suspend fun getDataFromAPI(@Query("page")page:Int): RickMortyApiData
}