package com.webtoon.webtoonbox.domain.usecase

import androidx.paging.PagingData
import com.webtoon.webtoonbox.domain.model.Webtoon
import com.webtoon.webtoonbox.domain.repository.WebtoonListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetWebtoonsUseCase @Inject constructor(
    private val webtoonListRepository: WebtoonListRepository
) {
    operator fun invoke(
        keyword: String?,
        provider: String? = null,
        sort: String? = "ASC",
        isUpdated: Boolean? = null,
        isFree: Boolean? = true,
        updateDay: Boolean? = null
    ): Flow<PagingData<Webtoon>> = webtoonListRepository.getWebtoonsPaged(keyword, provider, sort, isUpdated, isFree, updateDay)
}