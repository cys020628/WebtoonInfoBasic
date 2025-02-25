package com.webtoon.webtoonbox.di

import com.webtoon.webtoonbox.data.repository.WebtoonListRepositoryImpl
import com.webtoon.webtoonbox.domain.repository.WebtoonListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun webtoonListRepository(webtoonListRepositoryImpl: WebtoonListRepositoryImpl): WebtoonListRepository
}