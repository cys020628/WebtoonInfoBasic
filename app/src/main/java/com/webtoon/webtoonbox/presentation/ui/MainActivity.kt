package com.webtoon.webtoonbox.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.webtoon.webtoonbox.presentation.navigation.NavScreen
import com.webtoon.webtoonbox.presentation.theme.WebtoonBoxTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WebtoonBoxTheme {
               NavScreen()
            }
        }
    }
}