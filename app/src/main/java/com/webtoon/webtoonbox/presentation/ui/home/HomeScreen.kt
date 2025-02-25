package com.webtoon.webtoonbox.presentation.ui.home

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.webtoon.webtoonbox.R
import com.webtoon.webtoonbox.domain.model.ProviderType
import com.webtoon.webtoonbox.domain.model.Webtoon
import timber.log.Timber

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val webtoons = homeViewModel.webtoons.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        homeViewModel.getWebtoons()
    }

    Scaffold(
        modifier = Modifier.background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // 가로 4개
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(webtoons.itemCount) { index ->
                    val webtoon = webtoons[index]
                    if (webtoon != null) {
                        WebtoonItem(navController, webtoon)
                    }
                }
            }
        }
    }
}

@Composable
fun WebtoonItem(navController: NavHostController, webtoon: Webtoon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // 정사각형 형태 유지
            .clickable {
                Timber.e("무료인가 : ${webtoon.isFree}\n 제목 : ${webtoon.title}\n 사진 ${webtoon.thumbnail}\n 링크: ${webtoon.url} || ㅇㅁㄴㅇ : ${webtoon.updateDays}")
                navController.navigate("webview/${Uri.encode(webtoon.url)}")
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box( // Box를 사용하여 내부 요소의 위치를 조정
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val webtoonIcon = when (webtoon.provider) {
                ProviderType.NAVER -> R.drawable.naver_webtoon_icon
                ProviderType.KAKAO -> R.drawable.kakao_webtoon_icon_1
                ProviderType.KAKAO_PAGE -> R.drawable.kakao_page_webtoon_icon_1
                ProviderType.UNKNOWN -> R.drawable.main_logo
            }

            // 좌측 상단 배치
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(webtoonIcon)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(20.dp) // 크기 조정
                    .clip(RoundedCornerShape(8.dp))
                    .align(Alignment.TopStart) // 좌측 상단 정렬
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(webtoon.thumbnail)
                            .crossfade(true)
                            .setHeader("User-Agent", "Mozilla/5.0") // 웹툰 서버 차단 우회
                            .build()
                    ),
                    contentDescription = webtoon.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = webtoon.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Composable
fun WebViewScreen(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                setOnClickListener { null }
                loadUrl(url)
            }
        },
        modifier = Modifier
            .fillMaxSize()
    )
}