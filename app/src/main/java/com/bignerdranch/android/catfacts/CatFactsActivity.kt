package com.bignerdranch.android.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CatFactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_facts)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.FragmentContainer, CatFactsFragment.newInstance())
                .commit()
        }
    }
}
