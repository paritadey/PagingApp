package com.parita.paginationapp

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.parita.paginationapp.PaginationConstant.CHARACTER_EPISODE
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 8.dp))
            LoadImageFromURL()
            Spacer(modifier = Modifier.padding(top = 2.dp))
            CharacterDetails(data = "Character Details", characterType = "NONE")
            Spacer(modifier = Modifier.padding(top = 4.dp))
            arguments?.getString(PaginationConstant.CHARACTER_NAME)
                ?.let { CharacterDetails(it, PaginationConstant.CHARACTER_NAME) }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            arguments?.getString(PaginationConstant.CHARACTER_SPECIES)
                ?.let { CharacterDetails(it, PaginationConstant.CHARACTER_SPECIES) }
            Spacer(modifier = Modifier.padding(top = 8.dp))
            CharacterDetails(showDateTime(), PaginationConstant.CHARACTER_CREATED)
            Spacer(modifier = Modifier.padding(top = 4.dp))
            ShowEpisodeList()
        }
    }

    private fun showDateTime(): String {
        val timeStamp: Long = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault()).parse(
            arguments?.getString(PaginationConstant.CHARACTER_CREATED)
        ).getTime()
        return SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault()).format(timeStamp)
    }

    @Composable
    fun ShowEpisodeList() {
        val episodeList: ArrayList<String> =
            requireArguments().getSerializable(CHARACTER_EPISODE) as ArrayList<String>
        LazyColumn(contentPadding = PaddingValues(12.dp)) {
            items(items = episodeList) {
                EpisodeListItem(title = it.takeLast(3).replace("e", "").replace("/", ""))
            }
        }
    }

    @Composable
    fun CharacterDetails(data: String, characterType: String) {
        when {
            characterType.equals(PaginationConstant.CHARACTER_NAME) -> Text(
                "Name : $data",
                color = Color.Black,
                fontSize = 16.sp
            )
            characterType.equals(PaginationConstant.CHARACTER_SPECIES) -> Text(
                "Species : $data",
                color = Color.Black,
                fontSize = 16.sp
            )
            characterType.equals(PaginationConstant.CHARACTER_CREATED) -> Text(
                "Created : $data",
                color = Color.Black,
                fontSize = 16.sp
            )
            else -> Text(text = data, color = Color.Blue, fontSize = 18.sp)
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
        else Text("Loading image...", color = Color.Black, fontSize = 18.sp)
    }
}