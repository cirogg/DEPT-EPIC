package com.cirogg.deptepicchallenge.api

import com.cirogg.deptepicchallenge.model.response.DatesResponse
import retrofit2.Response
import retrofit2.http.GET

interface EpicApi {
    @GET("api/enhanced/all")
    suspend fun getAvailableDates(): Response<MutableList<DatesResponse>>

}