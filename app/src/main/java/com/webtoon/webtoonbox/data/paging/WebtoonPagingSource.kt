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

    /**
     * Paging 데이터를 로드하는 메서드
     *
     * @param params Paging 라이브러리가 제공하는 로드 매개변수 (key, loadSize 포함)
     * @return LoadResult (성공 시 LoadResult.Page, 실패 시 LoadResult.Error)
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Webtoon> {
        val page = params.key ?:1
        val response = webtoonApi.getWebtoonList(page = page, perPage =  params.loadSize, keyword = keyword)

        // 웹툰 리스트가 비어있지 않은 경우, 데이터를 반환
        return if(response.webtoons.isNotEmpty()) {
            LoadResult.Page(
                data = response.webtoons.map { it.toDomain() },
                prevKey =   if(page == 1) null else page - 1,
                nextKey = if (response.isLastPage) null else page + 1
            )
        }else {
            // 데이터가 없으면 예외 처리
            LoadResult.Error(Exception("Empty Webtoon List"))
        }
    }

    /**
     * 페이징 데이터를 새로고침할 때 기준이 되는 페이지 키를 반환하는 메서드
     *
     * @param state PagingState 객체 (현재 상태 정보 포함)
     * @return 새로고침할 때 사용할 페이지 키
     */
    override fun getRefreshKey(state: PagingState<Int, Webtoon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}