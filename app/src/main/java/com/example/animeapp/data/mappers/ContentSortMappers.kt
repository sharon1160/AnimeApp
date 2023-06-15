package com.example.animeapp.data.mappers

import com.example.animeapp.domain.enums.ContentSort
import com.example.type.MediaSort

fun ContentSort.toApolloModel(): MediaSort = when (this) {
    ContentSort.ID -> MediaSort.ID
    ContentSort.ID_DESC -> MediaSort.ID_DESC
    ContentSort.TITLE_ROMAJI -> MediaSort.TITLE_ROMAJI
    ContentSort.TITLE_ROMAJI_DESC -> MediaSort.TITLE_ROMAJI_DESC
    ContentSort.TITLE_ENGLISH -> MediaSort.TITLE_ENGLISH
    ContentSort.TITLE_ENGLISH_DESC -> MediaSort.TITLE_ENGLISH_DESC
    ContentSort.TITLE_NATIVE -> MediaSort.TITLE_NATIVE
    ContentSort.TITLE_NATIVE_DESC -> MediaSort.TITLE_NATIVE_DESC
    ContentSort.TYPE -> MediaSort.TYPE
    ContentSort.TYPE_DESC -> MediaSort.TYPE_DESC
    ContentSort.FORMAT -> MediaSort.FORMAT
    ContentSort.FORMAT_DESC -> MediaSort.FORMAT_DESC
    ContentSort.START_DATE -> MediaSort.START_DATE
    ContentSort.START_DATE_DESC -> MediaSort.START_DATE_DESC
    ContentSort.END_DATE -> MediaSort.END_DATE
    ContentSort.END_DATE_DESC -> MediaSort.END_DATE_DESC
    ContentSort.SCORE -> MediaSort.SCORE
    ContentSort.SCORE_DESC -> MediaSort.SCORE_DESC
    ContentSort.POPULARITY -> MediaSort.POPULARITY
    ContentSort.POPULARITY_DESC -> MediaSort.POPULARITY_DESC
    ContentSort.TRENDING -> MediaSort.TRENDING
    ContentSort.TRENDING_DESC -> MediaSort.TRENDING_DESC
    ContentSort.EPISODES -> MediaSort.EPISODES
    ContentSort.EPISODES_DESC -> MediaSort.EPISODES_DESC
    ContentSort.DURATION -> MediaSort.DURATION
    ContentSort.DURATION_DESC -> MediaSort.DURATION_DESC
    ContentSort.STATUS -> MediaSort.STATUS
    ContentSort.STATUS_DESC -> MediaSort.STATUS_DESC
    ContentSort.CHAPTERS -> MediaSort.CHAPTERS
    ContentSort.CHAPTERS_DESC -> MediaSort.CHAPTERS_DESC
    ContentSort.VOLUMES -> MediaSort.VOLUMES
    ContentSort.VOLUMES_DESC -> MediaSort.VOLUMES_DESC
    ContentSort.UPDATED_AT -> MediaSort.UPDATED_AT
    ContentSort.UPDATED_AT_DESC -> MediaSort.UPDATED_AT_DESC
    ContentSort.SEARCH_MATCH -> MediaSort.SEARCH_MATCH
    ContentSort.FAVOURITES -> MediaSort.FAVOURITES
    ContentSort.FAVOURITES_DESC -> MediaSort.FAVOURITES_DESC
}
