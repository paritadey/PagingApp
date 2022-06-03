package com.parita.paginationapp

import com.parita.paginationapp.network.ResultsItem

data class CharacterClickListener(val clickListener: (character: ResultsItem) -> Unit)