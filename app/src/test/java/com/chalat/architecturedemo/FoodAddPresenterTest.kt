package com.chalat.architecturedemo

import com.chalat.architecturedemo.add.AddContract
import com.chalat.architecturedemo.add.AddPresenter
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
class FoodAddPresenterTest {

    private lateinit var presenter: AddContract.Presenter
    @Mock
    private lateinit var foodRepository: FoodDataSource
    @Mock
    private lateinit var view: AddContract.View

    private val mockFoodAdd = FoodMenuItem("PadThai", "mockUrlPadThai")


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = AddPresenter(view, foodRepository)
    }

    @Test
    fun addFoodAndThenFoodRepositoryAddFoodMatch() {
        // Given
        Mockito.`when`(foodRepository.addFood(any()))
                .thenReturn(Completable.complete())
        // When
        presenter.selectedImage(mockFoodAdd.foodImageUrl)
        presenter.addFood(mockFoodAdd.foodTitle)
        // Then
        Mockito.verify(foodRepository).addFood(eq(mockFoodAdd))
    }

}
