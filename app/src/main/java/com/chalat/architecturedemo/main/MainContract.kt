package com.chalat.architecturedemo.main

import com.chalat.architecturedemo.data.entities.FoodMenuItem

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
interface MainContract {

    interface View {
        fun updateView(randomFoodList: List<FoodMenuItem>)
    }

    interface Presenter {
        fun getRandomFood()
    }

}