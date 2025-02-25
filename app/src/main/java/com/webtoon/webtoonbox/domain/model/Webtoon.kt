package com.webtoon.webtoonbox.domain.model

// 개별 웹툰 도메인 모델
data class Webtoon(
    val id: String,
    val title: String,
    val provider: ProviderType,
    val updateDays: List<DayOfWeek>,
    val url: String,
    val thumbnail: String, // 대표 썸네일 1개만 사용
    val isCompleted: Boolean,
    val isFree: Boolean,
    val isUpdated: Boolean,
    val ageRating: Int,
    val freeWaitHours: Int,
    val authors: List<String>
)

// 웹툰 목록을 감싸는 모델
data class SortedByLatestDataModel(
    val webtoons: List<Webtoon>,
    val total: Int,
    val isLastPage: Boolean
)

// 웹툰 제공사 Enum
enum class ProviderType {
    NAVER, KAKAO, KAKAO_PAGE, UNKNOWN
}

// 요일 Enum
enum class DayOfWeek {
    MON, TUE, WED, THU, FRI, SAT, SUN, FINISH
}