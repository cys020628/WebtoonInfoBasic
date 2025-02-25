package com.webtoon.webtoonbox.domain.repository

import androidx.paging.PagingData
import com.webtoon.webtoonbox.domain.model.Webtoon
import kotlinx.coroutines.flow.Flow

interface WebtoonListRepository {

    fun getWebtoonsPaged(
        keyword: String?,
        provider: String? = null,
        sort: String? = "ASC",
        isUpdated: Boolean? = null,
        isFree: Boolean? = true,
        updateDay: Boolean? = null
    ): Flow<PagingData<Webtoon>>
}