package com.example.urbandictionary.di

import com.example.urbandictionary.data.ListData
import com.example.urbandictionary.data.UrbanResponseData
import com.example.urbandictionary.repositories.UrbanDictionaryRepository
import com.example.urbandictionary.repositories.UrbanDictionaryRepositoryImpl
import com.example.urbandictionary.repositories.UrbanDictionaryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.delay
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object TestRepositoryModule {

    @Provides
    @Singleton
    fun provideUrbanRepository(
        urbanDictionaryService: UrbanDictionaryService
    ): UrbanDictionaryRepository {
        return UrbanDictionaryRepositoryImpl(urbanDictionaryService)
    }
}

class MockUrbanDictionaryService : UrbanDictionaryService {

    override suspend fun searchUrbanItems(term: String): UrbanResponseData {
        delay(1000)
        return UrbanResponseData(
            listOf(
                ListData(
                    "test_definition",
                    "test_permalink",
                    100,
                    listOf(
                        "test_sound_one", "test_sound_two"
                    ),
                    "test_author",
                    "test_word",
                    1,
                    "test_current_vote",
                    "test_written_on",
                    "test_example",
                    50
                )
            )
        )
    }

}