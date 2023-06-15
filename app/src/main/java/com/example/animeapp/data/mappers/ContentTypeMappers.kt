package com.example.animeapp.data.mappers

import com.example.animeapp.domain.enums.ContentType
import com.example.type.MediaType

fun ContentType.toApolloModel() = when (this) {
    ContentType.ANIME -> MediaType.ANIME
    ContentType.MANGA -> MediaType.MANGA
}
