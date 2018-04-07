package com.chalat.architecturedemo.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.chalat.architecturedemo.R
import com.chalat.architecturedemo.data.entities.FoodMenuItem
import kotlinx.android.synthetic.main.view_food_menu.view.*

/**
 *
 * Created by Chalat Chansima on 4/7/18.
 *
 */
class FoodMenuViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    fun bindView(foodMenuItem: FoodMenuItem) {
        itemView.foodMenuTextView.text = foodMenuItem.foodTitle
        Glide.with(itemView.context)
                .load(foodMenuItem.foodImageUrl)
                .into(itemView.foodMenuImageView)
    }

    companion object {
        fun getViewHolder(parent: ViewGroup?): FoodMenuViewHolder {
            val itemView = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.view_food_menu, parent, false)
            return FoodMenuViewHolder(itemView)
        }
    }
}