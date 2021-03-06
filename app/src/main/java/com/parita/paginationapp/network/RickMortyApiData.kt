package com.parita.paginationapp.network

import com.google.gson.annotations.SerializedName

data class RickMortyApiData(val info: Info, val results: List<ResultsItem>)

data class Info(val count: Int?, val pages: String?, val next: String?, val prev: String?)

data class ResultsItem(val image: String?, val species: String?, val name: String?)
