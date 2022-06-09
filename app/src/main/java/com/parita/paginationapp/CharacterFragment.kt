package com.parita.paginationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.parita.paginationapp.PaginationConstant.CHARACTER_CREATED
import com.parita.paginationapp.PaginationConstant.CHARACTER_EPISODE
import com.parita.paginationapp.PaginationConstant.CHARACTER_ID
import com.parita.paginationapp.PaginationConstant.CHARACTER_IMAGE
import com.parita.paginationapp.PaginationConstant.CHARACTER_NAME
import com.parita.paginationapp.PaginationConstant.CHARACTER_SPECIES
import com.parita.paginationapp.network.ResultsItem
import kotlinx.coroutines.flow.Flow

class CharacterFragment : Fragment() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CharacterData()
            }
        }
    }

    @Composable
    fun CharacterData() {
        EpisodeData {
            val mBundle = Bundle()
            val mFragment = EpisodeFragment()
            val list: ArrayList<String> = ArrayList()
            it.episode.let { it1 -> list.addAll(it1) }
            mBundle.putString(CHARACTER_ID, it.id)
            mBundle.putString(CHARACTER_NAME, it.name)
            mBundle.putString(CHARACTER_IMAGE, it.image)
            mBundle.putString(CHARACTER_SPECIES, it.species)
            mBundle.putString(CHARACTER_CREATED, it.created)
            mBundle.putStringArrayList(CHARACTER_EPISODE, list)
            mFragment.arguments = mBundle
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction() as FragmentTransaction
            transaction.replace(R.id.frame_container, mFragment).addToBackStack(null).commit()
        }
    }

    @Composable
    fun EpisodeData(selectedItem: (ResultsItem) -> Unit) {
        val response: Flow<PagingData<ResultsItem>> = viewModel.response
        val characterListItem: LazyPagingItems<ResultsItem> = response.collectAsLazyPagingItems()
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(
                    items = characterListItem,
                    itemContent = {
                        CharacterListItem(resultsItem = it!!, selectedItem)
                    }
                )
            }
        }
    }
}