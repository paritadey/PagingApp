package com.parita.paginationapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    companion object {
        val baseURL = "https://rickandmortyapi.com/api/"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}