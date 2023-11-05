package com.mwaibanda.momentum.android.presentation.messages

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


data class Message(
    val passages: List<Passage>
)
data class Passage(
    val header: String? = null,
    val verse: String? = null,
    val message: String? = null
)
@Composable
fun MessageDetailScreen() {
    val message = Message(
        passages = listOf(
            Passage(
                header = "Matthew 23:1-12",
            ),
            Passage(
                verse = "Matthew 23:1-3",
                message = """
                    Jesus said to the crowds and to his disciples: 2 “The
                    teachers of the law and the Pharisees sit in Moses’ seat.
                    3 So you must be careful to do everything they tell you. 
                    But do not do what they do, for they do not practice what they preach. 
                """.trimIndent()
            ),
            Passage(
                header = "FAKERS",
            ),
            Passage(
                verse = "Matthew 23:4",
                message = """
                    4 They tie up heavy, cumbersome loads and put them on 
                    other people’s shoulders, BUT they themselves are not
                    willing to lift a finger to move them 
                """.trimIndent()
            ),
            Passage(
                header = "FAULT FINDERS",
            ),
            Passage(
                verse = "Matthew 23:5-7",
                message = """
                    5 “Everything they do is done for people to see: They make
                    their phylacteries wide and the tassels on their garments long; 
                    6 they love the place of honor at banquets and the most 
                    important seats in the synagogues; 7 they love to be 
                    greeted with respect in the marketplaces and to be called
                    ‘Rabbi’ by others.
                """.trimIndent()
            ),
        )
    )
    Column(
        Modifier
            .padding(bottom = 50.dp)
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://github.com/MwaiBanda/Momentum/assets/49708426/e5b6e89c-57bd-4c47-9eb0-d3a05a4f97fe")
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
                passage.header?.let { header ->
                    Text(text = header)
                } ?: kotlin.run {
                    Text(text = passage.verse ?: "", fontWeight = FontWeight.Bold)
                    Text(text = buildAnnotatedString {
                        (passage.message ?: "").forEach {
                            if (it.isDigit()) {
                                withStyle(SpanStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color.Gray)){
                                    append(it)
                                }
                            } else {
                                append(it)
                            }
                        }
                    })
                }
//                Text(text = "Add Notes", fontSize = 11.sp)
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}