package com.bignerdranch.android.catfacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CatFactsViewModel: ViewModel() {
    val factItemLiveData: LiveData<List<FactItem>>

    init {
        factItemLiveData = CatFactsFetchr().fetchContents()
    }

}