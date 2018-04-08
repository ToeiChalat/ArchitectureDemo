package com.chalat.architecturedemo.main

import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import io.reactivex.Observable
import timber.log.Timber

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
class MainViewModel(private val foodRepository: FoodDataSource) {

    fun getRandomFood(): Observable<List<FoodMenuItem>> {
        return foodRepository.getFoodList()
                .map { random(it) }
    }

    private fun random(foodList: List<FoodMenuItem>): List<FoodMenuItem> {
        return foodList.shuffled()
    }

}