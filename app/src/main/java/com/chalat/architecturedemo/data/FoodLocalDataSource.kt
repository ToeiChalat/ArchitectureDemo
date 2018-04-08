package com.chalat.architecturedemo.data

import android.content.SharedPreferences
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import com.chalat.architecturedemo.data.entities.FoodMenuList
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Observable

/**
 *
 * Created by Chalat Chansima on 4/7/18.
 *
 */
class FoodLocalDataSource(private val sharedPreferences: SharedPreferences) : FoodDataSource {

    private val menuListKey = "com.chalat.architecturedemo.menuListKey"
    private val gson = Gson()

    override fun getFoodList(): Observable<List<FoodMenuItem>> {
        var menuList = getMenuListFromSharedPreference()
        if (menuList.isNullOrBlank()) {
            menuList = seed()
        }
        return Observable.just(
                gson.fromJson(
                        menuList,
                        FoodMenuList::class.java
                ))
                .map { it.foodList }
    }

    private fun getMenuListFromSharedPreference() = sharedPreferences.getString(menuListKey, "")

    override fun addFood(foodItem: FoodMenuItem): Completable {
        return getFoodList()
                .doOnNext {
                    val newList = ArrayList(it).apply { add(foodItem) }
                    val foodList = FoodMenuList(newList)
                    sharedPreferences.edit()
                            .putString(menuListKey, gson.toJson(foodList, FoodMenuList::class.java))
                            .apply()
                }
                .flatMapCompletable { Completable.complete() }
    }

    private fun seed(): String {
        val menuList = gson.toJson(seedData, FoodMenuList::class.java)
        sharedPreferences.edit()
                .putString(menuListKey, menuList)
                .apply()
        return menuList
    }

    private val seedData = FoodMenuList(arrayListOf(
            FoodMenuItem("Pad Thai", "https://cdn.vox-cdn.com/uploads/chorus_image/image/56340687/shirmp_stirfry_sushi_siam_miami.0.jpg"),
            FoodMenuItem("Tom Yum Kung", "https://cdn.vox-cdn.com/uploads/chorus_image/image/56340689/s1.0.jpg"),
            FoodMenuItem("Leng", "https://img01.siam2nite.com/8cqwYBa-M8lVCWXN6yD0S71kvV0=/smart/magazine/articles/711/cover_large_p1bug3hd59taantairkuon8k05.jpg"),
            FoodMenuItem("Fried Chicken", "https://i1.wp.com/media.hungryforever.com/wp-content/uploads/2015/07/friedchicken1.jpg?zoom=2&ssl=1?w=1269&strip=all&quality=80"),
            FoodMenuItem("Hamburger", "https://24.p3k.hu/app/uploads/2014/08/hamburger1-e1452264590881-1024x576.jpg"),
            FoodMenuItem("Steak", "https://www.save.ca/community/wp-content/uploads/2016/03/Fotolia_74568616_Subscription_XXL.jpg")
    ))

}
