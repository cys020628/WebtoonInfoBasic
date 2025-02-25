package com.webtoon.webtoonbox.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.webtoon.webtoonbox.presentation.ui.home.HomeScreen
import com.webtoon.webtoonbox.presentation.ui.home.WebViewScreen
import com.webtoon.webtoonbox.presentation.ui.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = MainNavItem.Splash.route,
        modifier = modifier
    ) {
        // 스플래쉬 화면
        composable(MainNavItem.Splash.route) { SplashScreen(navController) }
        // 홈 화면
        composable(BottomNavItem.Home.route) { HomeScreen(navController) }
        // 웹뷰 화면
        composable("${MainNavItem.WebView.route}/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url)
        }
    }
}