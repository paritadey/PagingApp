package com.parita.paginationapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.parita.paginationapp.databinding.FragmentEpisodeBinding

class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentEpisodeBinding?>(inflater, R.layout.fragment_episode, container, false).apply {
            Glide.with(detailImage).load(arguments?.getString(PaginationConstant.CHARACTER_IMAGE)).circleCrop().into(detailImage)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            id = arguments?.getString(PaginationConstant.CHARACTER_ID)!!
        }
    }
}