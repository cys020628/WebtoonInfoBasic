package com.webtoon.webtoonbox.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.webtoon.webtoonbox.data.mapper.toDomain
import com.webtoon.webtoonbox.data.remote.WebtoonApi
import com.webtoon.webtoonbox.domain.model.Webtoon

class WebtoonPagingSource(
    private val webtoonApi:WebtoonApi,
    private val keyword:String
):PagingSource<Int,Webtoon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Webtoon> {
        val page = params.key ?:1
        val response = webtoonApi.getWebtoonList(page = page, perPage =  params.loadSize, keyword = keyword)

        return if(response.webtoons.isNotEmpty()) {
            LoadResult.Page(
                data = response.webtoons.map { it.toDomain() },
                prevKey =   if(page == 1) null else page - 1,
                nextKey = if (response.isLastPage) null else page + 1
            )
        }else {
            LoadResult.Error(Exception("Empty Webtoon List"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Webtoon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}