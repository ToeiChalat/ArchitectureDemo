package com.chalat.architecturedemo.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chalat.architecturedemo.R
import com.chalat.architecturedemo.add.AddActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit()
        }

        fab.setOnClickListener { _ ->
            startActivity(Intent(this, AddActivity::class.java))
        }
    }
}
