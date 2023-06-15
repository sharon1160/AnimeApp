package com.example.animeapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animeapp.domain.Anime
import com.example.animeapp.domain.enums.ContentType
import com.example.animeapp.domain.usecase.GetAnimesUseCase

class AnimesPagingSource (
    private val getAnimesUseCase: GetAnimesUseCase,
    private val query: String,
    private val typeFilter: ContentType
) : PagingSource<Int, Anime>() {

    override fun getRefreshKey(state: PagingState<Int, Anime>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Anime> {
        return try {
            val currentPage = params.key ?: 1
            val responseList: List<Anime> =
                getAnimesUseCase.execute(
                    page = currentPage,
                    search = query,
                    type = typeFilter
                )
            return LoadResult.Page(
                data = responseList,
                prevKey = null,
                nextKey = if (responseList.isNotEmpty()) currentPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
