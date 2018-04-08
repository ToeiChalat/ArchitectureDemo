package com.chalat.architecturedemo

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.chalat.architecturedemo.add.AddContract
import com.chalat.architecturedemo.add.AddPresenter
import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.FoodLocalDataSource
import com.chalat.architecturedemo.data.FoodRepository
import com.chalat.architecturedemo.main.MainContract
import com.chalat.architecturedemo.main.MainPresenter

/**
 *
 * Created by Chalat Chansima on 4/7/18.
 *
 */
object Injection {

    private const val sharedPreferencesKey = "RandomFoodSharedPreferencesKey"

    fun getMainPresenter(view: MainContract.View,
                         foodRepository: FoodDataSource): MainPresenter {
        return MainPresenter(view, foodRepository)
    }

    fun getAddPresenter(view: AddContract.View, repository: FoodDataSource)
            : AddContract.Presenter {
        return AddPresenter(view, repository)
    }

    fun getFoodRepository(context: Context): FoodDataSource {
        return FoodRepository(
                getFoodLocalRepository(getSharedPreference(context))
        )
    }

    private fun getFoodLocalRepository(sharedPreferences: SharedPreferences): FoodDataSource {
        return FoodLocalDataSource(sharedPreferences)
    }

    private fun getSharedPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences(sharedPreferencesKey, MODE_PRIVATE)
    }

}