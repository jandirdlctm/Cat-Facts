package com.bignerdranch.android.catfacts.api

import retrofit2.Call
import retrofit2.http.GET

interface CatFactsApi {
    @GET("facts?limit=100")
    fun fetchContents(): Call<CatFactsResponse>

}

