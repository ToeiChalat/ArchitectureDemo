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
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import kotlinx.android.synthetic.main.fragment_add.*
import com.sangcomz.fishbun.define.Define
import android.app.Activity.RESULT_OK
import android.net.Uri
import com.bumptech.glide.Glide
import java.util.ArrayList


class AddFragment : Fragment(), AddContract.View {

    private lateinit var addPresenter: AddContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val repository = Injection.getFoodRepository(it)
            addPresenter = Injection.getAddPresenter(this, repository)
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

    override fun showAlbum() {
        FishBun.with(this)
                .setImageAdapter(GlideAdapter())
                .setCamera(true)
                .setMaxCount(1)
                .setReachLimitAutomaticClose(true)
                .startAlbum()
    }

    override fun showSnackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    override fun addFoodFinish() {
        activity?.finish()
    }

    override fun showThumbNail(foodImageUri: String) {
        Glide.with(this)
                .load(foodImageUri)
                .into(foodAddImageView)
    }

    private val onClickSelectImage: (View) -> Unit = {
        addPresenter.clickSelectImage()
    }

    private val onConfirmAdd: (View) -> Unit = {
        val title = foodAddEditText.text.toString()
        addPresenter.addFood(title)
    }

    private fun onSelectedImage(imageData: ArrayList<Uri>?) {
        imageData?.let {
            addPresenter.selectedImage(it[0].toString())
        }
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
