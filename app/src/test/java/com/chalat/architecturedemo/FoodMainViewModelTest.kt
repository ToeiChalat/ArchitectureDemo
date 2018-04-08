package com.chalat.architecturedemo

import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import com.chalat.architecturedemo.main.MainViewModel
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
class FoodMainViewModelTest {

    private lateinit var viewModel: MainViewModel
    @Mock private lateinit var foodRepository: FoodDataSource

    private val mockFoodList = listOf(
            FoodMenuItem("PadThai", "mockUrlPadThai"),
            FoodMenuItem("Tom Yum Kung", "mockUrlTomYumKung")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(foodRepository)
    }

    @Test
    fun getRandomFoodThenCallRepositoryGetFoodList() {
        // Given
        `when`(foodRepository.getFoodList())
                .thenReturn(Observable.just(mockFoodList))
        // When
        viewModel.getRandomFood()
        // Then
        verify(foodRepository).getFoodList()
    }

}