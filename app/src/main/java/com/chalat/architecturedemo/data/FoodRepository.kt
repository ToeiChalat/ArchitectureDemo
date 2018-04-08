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

    override fun getFoodList(): Observable<List<FoodMenuItem>> {
        return foodLocalDataSource.getFoodList()
    }

    override fun addFood(foodItem: FoodMenuItem): Completable {
        return foodLocalDataSource.addFood(foodItem)
    }

}
