package com.chalat.architecturedemo.data

import com.chalat.architecturedemo.data.entities.FoodMenuItem
import io.reactivex.Completable
import io.reactivex.Observable

/**
 *
 * Created by Chalat Chansima on 4/7/18.
 *
 */
interface FoodDataSource {

    fun getRandomFood(): Observable<List<FoodMenuItem>>

    fun addFood(foodItem: FoodMenuItem): Completable
}