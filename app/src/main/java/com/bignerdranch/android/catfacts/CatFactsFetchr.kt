package com.bignerdranch.android.catfacts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bignerdranch.android.catfacts.api.CatFactsApi
import com.bignerdranch.android.catfacts.api.CatFactsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "CatFactsFetchr"

class CatFactsFetchr {
    private val catfactsApi: CatFactsApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        catfactsApi = retrofit.create(CatFactsApi::class.java)
    }

    fun fetchContents(): LiveData<List<FactItem>> {
        val responseLiveData: MutableLiveData<List<FactItem>> = MutableLiveData()
        val catfactsRequest: Call<CatFactsResponse> = catfactsApi.fetchContents()

        catfactsRequest.enqueue(object : Callback<CatFactsResponse> {
            override fun onFailure(call: Call<CatFactsResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch photos", t)
            }

            override fun onResponse(call: Call<CatFactsResponse>, response: Response<CatFactsResponse>) {
                Log.d(TAG, "Response received")
                val catfactsResponse: CatFactsResponse? = response.body()
                var factItems: List<FactItem> = catfactsResponse?.factItems
                    ?: mutableListOf()
                factItems = factItems.filterNot{
                    it.fact.isBlank()
                }
                responseLiveData.value = factItems
            }
        })
        return responseLiveData
    }
}