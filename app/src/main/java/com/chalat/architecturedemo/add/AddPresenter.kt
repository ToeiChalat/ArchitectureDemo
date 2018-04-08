package com.chalat.architecturedemo.add

import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import timber.log.Timber

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
class AddPresenter(private val view: AddContract.View,
                   private val repository: FoodDataSource) : AddContract.Presenter {

    private var foodImageUri = ""

    override fun addFood(title: String) {
        if (title.isNotBlank() && foodImageUri.isNotBlank()) {
            repository.addFood(FoodMenuItem(title, foodImageUri))
                    .subscribe({ view.addFoodFinish() }, { Timber.e(it) })
        } else {
            when {
                title.isBlank() -> {
                    view.showSnackBar("Title cannot be blank")
                }
                foodImageUri.isBlank() -> {
                    view.showSnackBar("Image cannot be blank")
                }
            }
        }
    }

    override fun clickSelectImage() {
        view.showAlbum()
    }

    override fun selectedImage(imageStringUri: String) {
        foodImageUri = imageStringUri
        view.showThumbNail(foodImageUri)
    }

}