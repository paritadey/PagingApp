package com.parita.paginationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.parita.paginationapp.PaginationConstant.CHARACTER_CREATED
import com.parita.paginationapp.PaginationConstant.CHARACTER_EPISODE
import com.parita.paginationapp.PaginationConstant.CHARACTER_ID
import com.parita.paginationapp.PaginationConstant.CHARACTER_IMAGE
import com.parita.paginationapp.PaginationConstant.CHARACTER_NAME
import com.parita.paginationapp.PaginationConstant.CHARACTER_SPECIES
import com.parita.paginationapp.databinding.FragmentCharacterBinding
import kotlinx.coroutines.flow.collectLatest
import java.util.ArrayList

class CharacterFragment : Fragment() {
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var binding: FragmentCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initViewModel()
    }

    private fun initRecyclerView(){
        val mBundle = Bundle()
        val mFragment = EpisodeFragment()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
             recyclerViewAdapter = RecyclerViewAdapter(CharacterClickListener {
                 mBundle.putString(CHARACTER_ID, it.id)
                 mBundle.putString(CHARACTER_NAME, it.image)
                 mBundle.putString(CHARACTER_IMAGE, it.image)
                 mBundle.putString(CHARACTER_SPECIES, it.species)
                 mBundle.putString(CHARACTER_CREATED, it.created)
                 mBundle.putStringArrayList(CHARACTER_EPISODE, it.episode as ArrayList<String>?)
                 mFragment.arguments = mBundle
                 val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction() as FragmentTransaction
                 transaction.replace(R.id.frame_container, mFragment).addToBackStack(null).commit()
             })
             adapter = recyclerViewAdapter
        }

    }
    private fun initViewModel() {
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.getRecyclerListData().collectLatest {
                recyclerViewAdapter.submitData(it)
            }
        }
    }
}