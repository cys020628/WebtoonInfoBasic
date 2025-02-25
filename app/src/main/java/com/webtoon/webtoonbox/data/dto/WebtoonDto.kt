package com.webtoon.webtoonbox.data.dto

import com.google.gson.annotations.SerializedName


data class WebtoonResponseDto(
    @SerializedName("webtoons") val webtoons: List<WebtoonDto>,
    @SerializedName("total") val total: Int,
    @SerializedName("isLastPage") val isLastPage: Boolean
)

data class WebtoonDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("updateDays") val updateDays: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("thumbnail") val thumbnail: List<String>,
    @SerializedName("isEnd") val isEnd: Boolean,
    @SerializedName("isFree") val isFree: Boolean,
    @SerializedName("isUpdated") val isUpdated: Boolean,
    @SerializedName("ageGrade") val ageGrade: Int,
    @SerializedName("freeWaitHour") val freeWaitHour: Int?, // null 가능
    @SerializedName("authors") val authors: List<String>
)