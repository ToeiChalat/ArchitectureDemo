package com.chalat.architecturedemo.add

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chalat.architecturedemo.Injection

import com.chalat.architecturedemo.R
import com.chalat.architecturedemo.data.FoodDataSource
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import kotlinx.android.synthetic.main.fragment_add.*
import com.sangcomz.fishbun.define.Define
import android.app.Activity.RESULT_OK
import android.net.Uri
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.ArrayList


class AddFragment : Fragment() {

    private lateinit var repository: FoodDataSource

    private var foodImageUri = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            repository = Injection.getFoodRepository(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        foodAddConfirmFab.setOnClickListener(onConfirmAdd)
        foodAddImageView.setOnClickListener(onClickSelectImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Define.ALBUM_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                val imageData = data?.getParcelableArrayListExtra<Uri>(Define.INTENT_PATH)
                onSelectedImage(imageData)
            }
        }
    }

    private val onClickSelectImage: (View) -> Unit = {
        FishBun.with(this)
                .setImageAdapter(GlideAdapter())
                .setCamera(true)
                .setMaxCount(1)
                .setReachLimitAutomaticClose(true)
                .startAlbum()
    }

    private val onConfirmAdd: (View) -> Unit = {
        val title = foodAddEditText.text.toString()
        if (title.isNotBlank() && foodImageUri.isNotBlank()) {
            repository.addFood(FoodMenuItem(title, foodImageUri))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::addFoodFinish, { Timber.e(it) })
        } else {
            when {
                title.isBlank() -> {
                    Snackbar.make(it, "Title cannot be blank", Snackbar.LENGTH_SHORT)
                            .show()
                }
                foodImageUri.isBlank() -> {
                    Snackbar.make(it, "Image cannot be blank", Snackbar.LENGTH_SHORT)
                            .show()
                }
            }
        }
    }

    private fun onSelectedImage(imageData: ArrayList<Uri>?) {
        imageData?.let {
            foodImageUri = it[0].toString()
            Glide.with(this)
                    .load(foodImageUri)
                    .into(foodAddImageView)
        }
    }

    private fun addFoodFinish() {
        activity?.finish()
    }

    companion object {
        fun newInstance(): AddFragment {
            val fragment = AddFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
