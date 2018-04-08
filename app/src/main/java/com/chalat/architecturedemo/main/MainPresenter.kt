package com.chalat.architecturedemo.main

import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import timber.log.Timber

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
class MainPresenter(private val view: MainContract.View,
                    private val foodRepository: FoodDataSource)
    :MainContract.Presenter {

    override fun getRandomFood() {
        foodRepository.getFoodList()
                .map { random(it) }
                .subscribe({ view.updateView(it) }, { Timber.e(it) })
    }

    private fun random(foodList: List<FoodMenuItem>): List<FoodMenuItem> {
        return foodList.shuffled()
    }

}