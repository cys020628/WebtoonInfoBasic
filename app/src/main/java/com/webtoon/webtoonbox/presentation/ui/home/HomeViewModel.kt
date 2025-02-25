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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWebtoonsUseCase: GetWebtoonsUseCase
) : ViewModel() {

    // UI 갱신을 위한 웹툰 리스트 상태 (StateFlow)
    private val _webtoons = MutableStateFlow<PagingData<Webtoon>>(PagingData.empty())
    val webtoons: StateFlow<PagingData<Webtoon>> = _webtoons.asStateFlow()

    init {
        getWebtoons()
    }

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
                    Log.d("WebtoonViewModel", "새로운 데이터 로드됨: $pagingData")
                    Timber.e("새로운 데이터 로드됨: $keyword")
                }
                .collectLatest { pagingData ->
                    _webtoons.value = pagingData
                }
        }
    }
}