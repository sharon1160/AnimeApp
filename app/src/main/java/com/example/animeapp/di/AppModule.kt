package com.example.animeapp.di

import android.app.Application
import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.example.animeapp.data.ApolloAnimeClient
import com.example.animeapp.domain.AnimeClient
import com.example.animeapp.domain.repository.AnimesRepository
import com.example.animeapp.domain.repository.AnimesRepositoryImpl
import com.example.animeapp.domain.repository.FavoritesRepository
import com.example.animeapp.domain.usecase.GetAnimeUseCase
import com.example.animeapp.domain.usecase.GetAnimesUseCase
import com.example.animeapp.domain.usecase.GetCharacterUseCase
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

    @Provides
    @Singleton
    fun provideGetCharacterUseCase(animeClient: AnimeClient): GetCharacterUseCase {
        return GetCharacterUseCase(animeClient)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(context: Context): FavoritesRepository {
        return FavoritesRepository(context)
    }

    @Provides
    @Singleton
    fun provideAnimesRepository(animesRepositoryImpl: AnimesRepositoryImpl): AnimesRepository {
        return animesRepositoryImpl
    }
}
