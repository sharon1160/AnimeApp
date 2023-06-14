package com.example.animeapp.di

import com.apollographql.apollo3.ApolloClient
import com.example.animeapp.data.ApolloAnimeClient
import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.domain.usecases.GetAnimeUseCase
import com.example.animeapp.domain.usecases.GetAnimesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://graphql.anilist.co")
            .build()
    }

    @Provides
    @Singleton
    fun provideAnimeClient(apolloClient: ApolloClient): AnimeClient {
        return ApolloAnimeClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetAnimesUseCase(animeClient: AnimeClient): GetAnimesUseCase {
        return GetAnimesUseCase(animeClient)
    }

    @Provides
    @Singleton
    fun provideGetAnimeUseCase(animeClient: AnimeClient): GetAnimeUseCase {
        return GetAnimeUseCase(animeClient)
    }
}
