package com.example.basictextfield2

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.hasMediaType
import androidx.compose.foundation.content.receiveContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.basictextfield2.ui.theme.BasicTextField2Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicTextField2Theme {
                val viewModel by viewModels<MainViewModel>()
                val state by viewModel.state.collectAsState()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        var uri by remember {
                            mutableStateOf(Uri.EMPTY)
                        }
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.height(150.dp),
                            contentScale = ContentScale.FillHeight
                        )
                        BasicTextField2(
                            state = state.textState,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .clip(RoundedCornerShape(5.dp))
                                .background(Color.LightGray)
                                .padding(horizontal = 16.dp)
                                .receiveContent(MediaType.All) { content ->
                                    if (content.hasMediaType(MediaType.Image)) {
                                        val clipData = content.clipEntry.clipData
                                        for (index in 0 until clipData.itemCount) {
                                            val item = clipData.getItemAt(index) ?: continue
                                            uri = item.uri ?: continue
                                        }
                                    }
                                    content
                                }
                        )
                    }
                }
            }
        }
    }
}