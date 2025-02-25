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

/**
 * ### 홈 화면 (HomeScreen)
 *
 * - 네이버/카카오 웹툰을 불러와 화면에 표시
 * - 무한 스크롤 (Paging3 적용)
 * - 웹툰을 클릭하면 웹뷰(WebView) 화면으로 이동
 */
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    // 웹툰 리스트를 Paging3의 LazyPagingItems로 변환하여 구독
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

                columns = GridCells.Fixed(3), // 3개의 열을 가진 그리드 레이아웃
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),// 수직 간격 조정
                horizontalArrangement = Arrangement.spacedBy(8.dp)// 수평 간격 조정
            ) {
                // 웹툰 리스트를 순회하면서 아이템 표시
                items(webtoons.itemCount) { index ->
                    val webtoon = webtoons[index]
                    if (webtoon != null) {
                        WebtoonItem(navController, webtoon) // 개별 웹툰 아이템 컴포넌트
                    }
                }
            }
        }
    }
}

/**
 * ### 개별 웹툰 아이템 (WebtoonItem)
 *
 * - 웹툰 썸네일 및 제공사 아이콘 표시
 * - 클릭 시 웹뷰로 이동하여 웹툰 상세 정보 확인 가능
 */
@Composable
fun WebtoonItem(navController: NavHostController, webtoon: Webtoon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // 정사각형 형태 유지
            .clickable {
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


/**
 * WebViewScreen: 웹뷰(WebView)를 통해 주어진 URL을 로드하는 Composable 함수
 *
 * @param url 웹뷰에서 로드할 웹 페이지의 URL
 */
@Composable
fun WebViewScreen(url: String) {
    // AndroidView를 사용하여 WebView를 직접 생성 및 설정
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // 🔹 WebView 설정 시작

                // 1️⃣ JavaScript 활성화 (웹 페이지에서 JavaScript 실행 가능하도록 설정)
                settings.javaScriptEnabled = true

                // 2️⃣ DOM Storage 활성화 (웹 페이지에서 로컬 스토리지 사용 가능하도록 설정)
                settings.domStorageEnabled = true

                // 3️⃣ 웹 콘텐츠가 화면 크기에 맞춰 보이도록 설정
                settings.loadWithOverviewMode = true // 컨텐츠를 화면에 맞게 조정

                // 4️⃣ 뷰포트를 지원하여 웹페이지가 모바일 화면에 최적화되도록 설정
                settings.useWideViewPort = true // 가로폭을 디바이스 화면 크기에 맞춤

                // 5️⃣ User-Agent를 모바일 크롬 브라우저처럼 설정
                // → 일부 사이트는 특정 User-Agent가 없으면 로딩을 막음 (예: 카카오 페이지)
                settings.userAgentString =
                    "Mozilla/5.0 (Linux; Android 10; SM-G975F) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.120 Mobile Safari/537.36"

                // 🔹 WebViewClient 설정
                // 기본적으로 WebView는 외부 브라우저를 실행할 수 있음 → WebView 내부에서 웹 페이지를 로드하도록 설정
                webViewClient = WebViewClient()

                // 🔹 주어진 URL을 로드 (웹 페이지 열기)
                loadUrl(url)
            }
        },
        // WebView를 화면 전체에 표시
        modifier = Modifier.fillMaxSize()
    )
}
