package com.chalat.architecturedemo.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.chalat.architecturedemo.data.entities.FoodMenuItem

/**
 *
 * Created by Chalat Chansima on 4/7/18.
 *
 */
class FoodMenuAdapter: RecyclerView.Adapter<FoodMenuViewHolder>() {

    private var foodMenuList = ArrayList<FoodMenuItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodMenuViewHolder {
        return FoodMenuViewHolder.getViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return foodMenuList.size
    }

    override fun onBindViewHolder(holder: FoodMenuViewHolder, position: Int) {
        holder.bindView(foodMenuList[position])
    }

    internal fun replaceData(foodMenuList: List<FoodMenuItem>) {
        this.foodMenuList = ArrayList(foodMenuList)
        notifyDataSetChanged()
    }

}
