package com.webtoon.webtoonbox.data.remote

import com.webtoon.webtoonbox.data.dto.WebtoonResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WebtoonApi {
    @GET("/webtoons")
    suspend fun getWebtoonList(
        // 웹툰 제목 및 작가 제목
        @Query("keyword") keyword: String,
        // 제공자 :  NAVER, KAKAO, KAKAO_PAGE
        @Query("provider") provider: String? = null,
        // 페이지 번호
        @Query("page") page: Int = 1,
        // 페이지당 항목수
        @Query("perPage") perPage: Int? = 30,
        // 올림차순: ASC 내림차순: DESC
        @Query("sort") sort: String? = "ASC",
        // 업데이트 됬는지
        @Query("isUpdated") isUpdated: Boolean? = true,
        // 무료인지
        @Query("isFree") isFree: Boolean? = true,
        // 업데이트 요일 : MON, TUE, WED, THU, FRI, SAT, SUN
        @Query("updateDay") updateDay: Boolean? = null,
    ): WebtoonResponseDto
}