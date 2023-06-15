package com.example.animeapp.domain.repository

import androidx.paging.PagingData
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType
import kotlinx.coroutines.flow.Flow

interface AnimesRepository {
    fun getAnimes(query: String, typeFilter: ContentType) : Flow<PagingData<Anime>>
}