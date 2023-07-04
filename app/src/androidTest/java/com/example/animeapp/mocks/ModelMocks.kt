package com.example.animeapp.mocks

import com.example.animeapp.domain.DetailedCharacter

object ModelMocks {
    val detailedCharacter = DetailedCharacter(
        id = 1,
        fullName = "fullName",
        nativeName = "nativeName",
        largeImage = "largeImage",
        gender = "gender",
        description = "description"
    )
}
