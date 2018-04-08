package com.chalat.architecturedemo.add

/**
 *
 * Created by Chalat Chansima on 4/8/18.
 *
 */
interface AddContract {

    interface View {
        fun showSnackBar(message: String)
        fun addFoodFinish()
        fun showAlbum()
        fun showThumbNail(foodImageUri: String)
    }

    interface Presenter {
        fun selectedImage(imageStringUri: String)
        fun addFood(title: String)
        fun clickSelectImage()
    }

}
