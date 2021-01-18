package com.example.urbandictionary.di

import com.example.urbandictionary.repositories.UrbanDictionaryRepository
import com.example.urbandictionary.repositories.UrbanDictionaryRepositoryImpl
import com.example.urbandictionary.repositories.UrbanDictionaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUrbanRepository(
        urbanDictionaryService: UrbanDictionaryService
    ): UrbanDictionaryRepository {
        return UrbanDictionaryRepositoryImpl(urbanDictionaryService)
    }
}