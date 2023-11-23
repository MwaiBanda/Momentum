package com.mwaibanda.momentum.android.presentation.message

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.mwaibanda.momentum.domain.models.Message


@Composable
fun MessageDetailScreen(message: Message) {
    LaunchedEffect(key1 = Unit, block = {
        Log.e("MessageDetailScreen", message.toString())
    })
    Column(
        Modifier
            .padding(bottom = 60.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(message.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "Sermon thumbnail",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .heightIn(max = 350.dp)
                .fillMaxWidth()
        )
        Column(Modifier.padding(horizontal = 10.dp)) {
            message.passages.forEach { passage ->
                Divider()
                Spacer(modifier = Modifier.height(15.dp))
                if (passage.header.isNullOrEmpty()) {
                    Text(text = passage.verse.toString(), fontWeight = FontWeight.Bold)
                    Text(text = buildAnnotatedString {
                        (passage.message.toString()).forEach {
                            if (it.isDigit()) {
                                withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)){
                                    append(it)
                                }
                            } else {
                                append(it)
                            }
                        }
                    })
                } else  {
                    Text(text = passage.header.toString())
                }
//                Text(text = "Add Notes", fontSize = 11.sp)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}