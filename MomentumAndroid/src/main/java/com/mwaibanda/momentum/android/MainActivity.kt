package com.mwaibanda.momentum.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mwaibanda.momentum.Greeting
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = greet())
        }
    }
}
