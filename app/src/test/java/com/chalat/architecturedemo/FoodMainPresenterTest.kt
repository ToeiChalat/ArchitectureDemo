package com.chalat.architecturedemo

import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import com.chalat.architecturedemo.main.MainContract
import com.chalat.architecturedemo.main.MainPresenter
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
class FoodMainPresenterTest {

    private lateinit var presenter: MainContract.Presenter
    @Mock private lateinit var foodRepository: FoodDataSource
    @Mock private lateinit var view: MainContract.View

    private val mockFoodList = listOf(
            FoodMenuItem("PadThai", "mockUrlPadThai"),
            FoodMenuItem("Tom Yum Kung", "mockUrlTomYumKung")
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view, foodRepository)
    }

    @Test
    fun getRandomFoodThenCallRepositoryGetFoodList() {
        // Given
        `when`(foodRepository.getFoodList())
                .thenReturn(Observable.just(mockFoodList))
        // When
        presenter.getRandomFood()
        // Then
        verify(foodRepository).getFoodList()
    }

}