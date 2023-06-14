package com.example.animeapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.animeapp.data.paging.AnimesPagingSource
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.domain.usecase.GetAnimesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AnimesRepositoryImpl @Inject constructor(
    private val getAnimesUseCase: GetAnimesUseCase
) : AnimesRepository {
    override fun getAnimes(query: String, typeFilter: ContentType): Flow<PagingData<Anime>> = Pager(
        initialKey = null,
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = false,
            prefetchDistance = 1
        ),
        pagingSourceFactory = {
            AnimesPagingSource(
                getAnimesUseCase,
                query,
                typeFilter
            )
        }
    ).flow

    companion object {
        const val PAGE_SIZE = 10
    }
}