package com.chalat.architecturedemo.add

import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
class AddViewModel(private val repository: FoodDataSource) {

    private var foodImageUri = ""

    internal val addFoodFinish = PublishSubject.create<Boolean>()
    internal val showSnackBar = PublishSubject.create<String>()
    internal val showAlbum = PublishSubject.create<Boolean>()
    internal val showThumbNail = BehaviorSubject.create<String>()

    fun addFood(title: String) {
        if (title.isNotBlank() && foodImageUri.isNotBlank()) {
            repository.addFood(FoodMenuItem(title, foodImageUri))
                    .subscribe({ addFoodFinish.onNext(true) }, { Timber.e(it) })
        } else {
            when {
                title.isBlank() -> {
                    showSnackBar.onNext("Title cannot be blank")
                }
                foodImageUri.isBlank() -> {
                    showSnackBar.onNext("Image cannot be blank")
                }
            }
        }
    }

    fun clickSelectImage() {
        showAlbum.onNext(true)
    }

    fun selectedImage(imageStringUri: String) {
        foodImageUri = imageStringUri
        showThumbNail.onNext(foodImageUri)
    }

}