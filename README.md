# 📖 Webtoon Viewer App

🚀 **Webtoon Viewer**는 네이버와 카카오 웹툰을 **무한 스크롤**(Paging3)로 불러오고,  
웹툰을 클릭하면 **웹뷰(WebView)** 를 통해 해당 웹툰의 상세 정보를 볼 수 있는 앱입니다.  
클린 아키텍처 기반으로 **싱글 모듈** 구조로 개발되었습니다.

---

## 📌 주요 기능
✅ **네이버 & 카카오 웹툰 목록 제공** (무한 스크롤 Paging3 적용)  
✅ **웹툰 클릭 시 웹뷰(WebView)로 상세 정보 이동**  
✅ **클린 아키텍처 (Single Module) 적용**  
✅ **Coil을 활용한 썸네일 이미지 로딩**  
✅ **Retrofit & Flow를 활용한 네트워크 비동기 처리**  
✅ **Hilt를 통한 의존성 주입(DI) 관리**  
✅ **Timber를 활용한 디버깅 로그 관리**  

---

## 🛠 사용 기술 및 라이브러리
| 기술 스택 | 설명 |
|-----------|----------------------------|
| **Paging3** | 무한 스크롤 적용 |
| **Retrofit2** | REST API 통신 |
| **Hilt** | 의존성 주입(DI) |
| **Coroutine & Flow** | 비동기 데이터 처리 |
| **Coil** | 이미지 로딩 |
| **Navigation Component** | 화면 전환 관리 |
| **Timber** | 디버깅 로그 |

---

## 🏛 아키텍처
본 프로젝트는 **클린 아키텍처** 원칙을 따르며, 단일 모듈 구조(Single Module)로 개발되었습니다.  

## 🚀 코드 실행
✅ BuildConfig(Module) -> 하단 코드 추가

``` kotlin
val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    defaultConfig {
        buildConfigField(
            "String",
            "WEBTOON_BASE_URL",
            "\"${properties.getProperty("WEBTOON_BASE_URL")}\""
        )
    }
}

  buildFeatures {
        buildConfig = true
    }

```

✅ local.properties -> 하단 코드 추가

``` kotlin
WEBTOON_BASE_URL = https://korea-webtoon-api-cc7dda2f0d77.herokuapp.com
```
