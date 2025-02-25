package com.webtoon.webtoonbox.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.webtoon.webtoonbox.domain.model.Webtoon
import com.webtoon.webtoonbox.domain.usecase.GetWebtoonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * HomeViewModel: 웹툰 데이터를 관리하고 UI에 제공하는 ViewModel
 *
 * @param getWebtoonsUseCase 웹툰 리스트를 가져오는 UseCase (Paging3 적용)
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWebtoonsUseCase: GetWebtoonsUseCase // 웹툰 데이터를 가져오는 UseCase 주입
) : ViewModel() {

    // UI 갱신을 위한 웹툰 리스트 상태 (StateFlow)
    private val _webtoons = MutableStateFlow<PagingData<Webtoon>>(PagingData.empty())

    // UI에서 구독할 수 있도록 변환
    val webtoons: StateFlow<PagingData<Webtoon>> = _webtoons.asStateFlow()

    /**
     * 웹툰 데이터를 요청하는 함수
     *
     * @param keyword 검색 키워드 (기본값: "")
     * @param provider 웹툰 제공사 (네이버, 카카오 등) (기본값: null)
     * @param sort 정렬 방식 (기본값: "ASC" - 오름차순)
     * @param isUpdated 최근 업데이트 여부 필터 (기본값: null)
     * @param isFree 무료 웹툰 필터 (기본값: true)
     * @param updateDay 특정 요일 업데이트 필터 (기본값: null)
     */
    fun getWebtoons(
        keyword: String = "",
        provider: String? = null,
        sort: String? = "ASC",
        isUpdated: Boolean? = null,
        isFree: Boolean? = true,
        updateDay: Boolean? = null
    ) {
        viewModelScope.launch {
            getWebtoonsUseCase(keyword, provider, sort, isUpdated, isFree, updateDay)
                .cachedIn(viewModelScope)
                .onEach { pagingData ->
                    // 새로운 데이터가 로드될 때 로그 출력 (디버깅 용도)
                    Timber.tag("WebtoonViewModel").d("새로운 데이터 로드됨: $pagingData")
                }
                .collectLatest { pagingData ->
                    // 최신 웹툰 리스트 상태 업데이트
                    _webtoons.value = pagingData
                }
        }
    }
}