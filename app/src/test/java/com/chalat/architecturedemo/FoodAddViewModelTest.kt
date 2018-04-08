package com.chalat.architecturedemo

import com.chalat.architecturedemo.add.AddViewModel
import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import com.chalat.architecturedemo.utils.any
import com.chalat.architecturedemo.utils.eq
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
class FoodAddViewModelTest {

    private lateinit var viewModel: AddViewModel
    @Mock private lateinit var foodRepository: FoodDataSource

    private val mockFoodAdd = FoodMenuItem("PadThai", "mockUrlPadThai")


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = AddViewModel(foodRepository)
    }

    @Test
    fun addFoodAndThenFoodRepositoryAddFoodMatch() {
        // Given
        Mockito.`when`(foodRepository.addFood(any()))
                .thenReturn(Completable.complete())
        // When
        viewModel.selectedImage(mockFoodAdd.foodImageUrl)
        viewModel.addFood(mockFoodAdd.foodTitle)
        // Then
        Mockito.verify(foodRepository).addFood(eq(mockFoodAdd))
    }

}
