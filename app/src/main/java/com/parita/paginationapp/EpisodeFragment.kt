package com.parita.paginationapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.parita.paginationapp.databinding.FragmentEpisodeBinding

class EpisodeFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                ImageDemo()
            }
        }
    }

    @Composable
    fun ImageDemo() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            arguments?.getString(PaginationConstant.CHARACTER_NAME)?.let { Text(text = it) }
            Log.d("TAG", "data: ${arguments?.getString(PaginationConstant.CHARACTER_ID)}")
            Spacer(modifier = Modifier.padding(top = 8.dp))
            LoadImageFromURL()
        }
    }

    @Composable
    fun LoadImageFromURL() {
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }
        Glide.with(LocalContext.current).asBitmap()
            .load(arguments?.getString(PaginationConstant.CHARACTER_IMAGE))
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    bitmap.value = resource
                }
            })
        val value = bitmap.value
        if(value!=null) Image(value.asImageBitmap(), contentDescription = "character image", Modifier.fillMaxWidth())
        else Text("Loading image...")
    }
}