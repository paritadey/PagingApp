package com.parita.paginationapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget

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
                CharacterDetailDemo()
            }
        }
    }

    @Composable
    fun CharacterDetailDemo() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 8.dp))
            LoadImageFromURL()
            Spacer(modifier = Modifier.padding(top = 2.dp))
            CharacterDetails(data = "Character Details", characterType = "NONE")
            Spacer(modifier = Modifier.padding(top = 4.dp))
            arguments?.getString(PaginationConstant.CHARACTER_NAME)?.let { CharacterDetails(it, PaginationConstant.CHARACTER_NAME) }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            arguments?.getString(PaginationConstant.CHARACTER_SPECIES)?.let { CharacterDetails(it, PaginationConstant.CHARACTER_SPECIES) }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            arguments?.getString(PaginationConstant.CHARACTER_CREATED)?.let { CharacterDetails(it, PaginationConstant.CHARACTER_CREATED) }
        }
    }

    @Composable
    fun CharacterDetails(data: String, characterType: String) {
        when {
            characterType.equals(PaginationConstant.CHARACTER_NAME) -> Text("Name : $data", color = Color.Black, fontSize = 16.sp)
            characterType.equals(PaginationConstant.CHARACTER_SPECIES) -> Text("Species : $data", color = Color.Black, fontSize = 16.sp)
            characterType.equals(PaginationConstant.CHARACTER_CREATED) -> Text("Created : $data", color = Color.Black, fontSize = 16.sp)
            else -> Text(text = data, color=Color.Blue, fontSize = 18.sp)
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
        if (value != null) Image(
            value.asImageBitmap(),
            contentDescription = "character image",
            Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(24.dp),
            contentScale = ContentScale.Crop,
        )
        else Text("Loading image...")
    }
}