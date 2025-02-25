package com.webtoon.webtoonbox.presentation.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.webtoon.webtoonbox.R

sealed class BottomNavItem(
    val route: String,
    val icon: @Composable () -> Unit,
    val label: String
) {
    data object Home : BottomNavItem(
        route = "home",
        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
        label = "Home"
    )

    data object Naver : BottomNavItem(
        route = "naver",
        icon = {   Image(
            painter = painterResource(id = R.drawable.naver_webtoon_icon),
            contentDescription = "앱 로고",
            modifier = Modifier.size(20.dp)
        ) },
        label = "Naver"
    )

    data object Kakao : BottomNavItem(
        route = "kakao",
        icon = {
            Image(
                painter = painterResource(id = R.drawable.kakao_webtoon_icon_1),
                contentDescription = "앱 로고",
                modifier = Modifier.size(20.dp)
            )
        },
        label = "Kakao"
    )

    data object KakaoPage : BottomNavItem(
        route = "kakaoPage",
        icon = {
            Image(
                painter = painterResource(id = R.drawable.kakao_page_webtoon_icon_1),
                contentDescription = "앱 로고",
                modifier = Modifier.size(20.dp)
            )
        },
        label = "KakaoPage"
    )
}

sealed class MainNavItem(val route: String, val label: String) {
    // 스플래쉬 화면
    data object Splash : MainNavItem("splash", "스플래쉬")
    // 웹뷰 화면
    data object WebView:MainNavItem("webview","웹뷰")
}