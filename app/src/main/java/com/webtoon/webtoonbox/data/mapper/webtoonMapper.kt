package com.webtoon.webtoonbox.data.mapper

import com.webtoon.webtoonbox.data.dto.WebtoonDto
import com.webtoon.webtoonbox.domain.model.DayOfWeek
import com.webtoon.webtoonbox.domain.model.ProviderType
import com.webtoon.webtoonbox.domain.model.Webtoon

fun WebtoonDto.toDomain(): Webtoon {
    return Webtoon(
        id = this.id,
        title = this.title,
        provider = when (this.provider.uppercase()) {
            "NAVER" -> ProviderType.NAVER
            "KAKAO" -> ProviderType.KAKAO
            "KAKAO_PAGE" -> ProviderType.KAKAO_PAGE
            else -> ProviderType.UNKNOWN
        },
        updateDays = this.updateDays.map {
            when (it.uppercase()) {
                "MON" -> DayOfWeek.MON
                "TUE" -> DayOfWeek.TUE
                "WED" -> DayOfWeek.WED
                "THU" -> DayOfWeek.THU
                "FRI" -> DayOfWeek.FRI
                "SAT" -> DayOfWeek.SAT
                "SUN" -> DayOfWeek.SUN
                else -> DayOfWeek.FINISH
            }
        },
        url = this.url,
        thumbnail = this.thumbnail.firstOrNull() ?: "", // 대표 이미지 1개만 사용
        isCompleted = this.isEnd,
        isFree = this.isFree,
        isUpdated = this.isUpdated,
        ageRating = this.ageGrade,
        freeWaitHours = this.freeWaitHour ?: 0, // null이면 기본값 0
        authors = this.authors
    )
}

// DTO 리스트 → Domain 모델 리스트 변환
fun List<WebtoonDto>.toDomain(): List<Webtoon> {
    return this.map { it.toDomain() }
}