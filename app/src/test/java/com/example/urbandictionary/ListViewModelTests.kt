package com.example.urbandictionary

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.urbandictionary.data.ListData
import com.example.urbandictionary.data.UrbanResponseData
import com.example.urbandictionary.repositories.UrbanDictionaryRepository
import com.example.urbandictionary.utils.NetworkResult
import com.example.urbandictionary.view.ItemCard
import com.example.urbandictionary.view.ListViewModel
import com.google.gson.annotations.SerializedName
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ListViewModelTests {

    private val mockUrbanDictionaryRepository = mockk<UrbanDictionaryRepository>(relaxed = true)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mockContext = mockk<Context>(relaxed = true)

    @Test
    fun checkInputSearch_validQuery_ShouldReturnSuccess(){
        testCoroutineRule.runBlockingTest {
            val observer = mockk<Observer<List<ItemCard>>>()
            val slot = slot<List<ItemCard>>()
            val list = arrayListOf<List<ItemCard>>()

            val query = "Rodrigo"

            coEvery {
                mockUrbanDictionaryRepository.searchUrbanItems(query)
            } returns NetworkResult.Success(
                UrbanResponseData(
                    listOf(
                        ListData(
                            "test_definition",
                            "test_permalink",
                            100,
                            listOf(
                                "test_sound_one","test_sound_two"
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
            )

            val viewModel = ListViewModel(
                mockContext,
                mockUrbanDictionaryRepository
            )
            viewModel.itemList.observeForever(observer)

            every {
                observer.onChanged(capture(slot))
            } answers {
                list.add(slot.captured)
            }

            viewModel.searchUrbanItems(query)

            val state = list[0]
            Assert.assertEquals(state[0].defId, 1)
            viewModel.itemList.removeObserver(observer)
        }
    }
}