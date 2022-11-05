package com.cirogg.deptepicchallenge.api

import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpicApi {
    @GET("api/enhanced/all")
    suspend fun getAvailableDates(): Response<MutableList<DatesResponse>>

    @GET("api/enhanced/date/{date}")
    suspend fun getAvailableImages(@Path("date") date: String): Response<ArrayList<ImagesResponse>>


}