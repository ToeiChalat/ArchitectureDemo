package com.chalat.architecturedemo.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chalat.architecturedemo.Injection
import com.chalat.architecturedemo.R
import com.chalat.architecturedemo.data.FoodDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import timber.log.Timber

class MainFragment : Fragment() {

    private val adapter = FoodMenuAdapter()
    private lateinit var foodRepository: FoodDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let {
            foodRepository = Injection.getFoodRepository(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        foodMenuRecyclerView.layoutManager = GridLayoutManager(context, 2)
        foodMenuRecyclerView.adapter = adapter
        foodMenuSwipeRefreshLayout.setOnRefreshListener { getRandomFood() }
    }

    override fun onResume() {
        super.onResume()
        getRandomFood()
    }

    private fun getRandomFood() {
        foodRepository.getRandomFood()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    adapter.replaceData(it)
                    foodMenuSwipeRefreshLayout.isRefreshing = false
                }, { Timber.e(it) })
    }

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
