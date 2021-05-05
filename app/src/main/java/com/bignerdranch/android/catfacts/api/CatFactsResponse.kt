package com.bignerdranch.android.catfacts.api

import com.bignerdranch.android.catfacts.FactItem
import com.google.gson.annotations.SerializedName

class CatFactsResponse {
    @SerializedName("data")
    lateinit var factItems: List<FactItem>

}