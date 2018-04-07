package com.chalat.architecturedemo.data

import com.chalat.architecturedemo.data.entities.FoodMenuItem
import io.reactivex.Completable
import io.reactivex.Observable

/**
 *
 * Created by Chalat Chansima on 4/7/18.
 *
 */
class FoodRepository(private val foodLocalDataSource: FoodDataSource) : FoodDataSource {

    override fun getRandomFood(): Observable<List<FoodMenuItem>> {
        return foodLocalDataSource.getRandomFood()
                .map {
                    val randomFoodList = ArrayList(it)
                    randomFoodList.shuffle()
                    randomFoodList
                }
    }

    override fun addFood(foodItem: FoodMenuItem): Completable {
        return foodLocalDataSource.addFood(foodItem)
    }

}
