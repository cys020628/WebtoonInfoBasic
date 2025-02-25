package com.webtoon.webtoonbox.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.webtoon.webtoonbox.data.paging.WebtoonPagingSource
import com.webtoon.webtoonbox.data.remote.WebtoonApi
import com.webtoon.webtoonbox.domain.model.Webtoon
import com.webtoon.webtoonbox.domain.repository.WebtoonListRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebtoonListRepositoryImpl @Inject constructor(
    private val api: WebtoonApi
) : WebtoonListRepository {
    override fun getWebtoonsPaged(
        keyword: String?,
        provider: String?,
        sort: String?,
        isUpdated: Boolean?,
        isFree: Boolean?,
        updateDay: Boolean?
    ): Flow<PagingData<Webtoon>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, // 한 페이지에 불러올 개수
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                WebtoonPagingSource(
                    webtoonApi = api,
                    keyword = keyword!!
                )
            }
        ).flow
    }
}