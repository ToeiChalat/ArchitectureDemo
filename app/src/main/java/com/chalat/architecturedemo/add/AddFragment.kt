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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.ArrayList


class AddFragment : Fragment() {

    private lateinit var addViewModel: AddViewModel
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            val repository = Injection.getFoodRepository(it)
            addViewModel = Injection.getAddViewModel(repository)
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

    override fun onResume() {
        bindViewModel()
        super.onResume()
    }

    override fun onPause() {
        unbindViewModel()
        super.onPause()
    }

    private fun bindViewModel() {
        disposable.add(
                addViewModel.addFoodFinish
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({addFoodFinish()}, { Timber.e(it) })
        )
        disposable.add(
                addViewModel.showAlbum
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({showAlbum()}, { Timber.e(it) })
        )
        disposable.add(
                addViewModel.showSnackBar
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({showSnackBar(it)}, { Timber.e(it) })
        )
        disposable.add(
                addViewModel.showThumbNail
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({showThumbNail(it)}, { Timber.e(it) })
        )
    }

    private fun unbindViewModel() {
        disposable.clear()
    }

    private fun showAlbum() {
        FishBun.with(this)
                .setImageAdapter(GlideAdapter())
                .setCamera(true)
                .setMaxCount(1)
                .setReachLimitAutomaticClose(true)
                .startAlbum()
    }

    private fun showSnackBar(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private fun addFoodFinish() {
        activity?.finish()
    }

    private fun showThumbNail(foodImageUri: String) {
        Glide.with(this)
                .load(foodImageUri)
                .into(foodAddImageView)
    }

    private val onClickSelectImage: (View) -> Unit = {
        addViewModel.clickSelectImage()
    }

    private val onConfirmAdd: (View) -> Unit = {
        val title = foodAddEditText.text.toString()
        addViewModel.addFood(title)
    }

    private fun onSelectedImage(imageData: ArrayList<Uri>?) {
        imageData?.let {
            addViewModel.selectedImage(it[0].toString())
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
