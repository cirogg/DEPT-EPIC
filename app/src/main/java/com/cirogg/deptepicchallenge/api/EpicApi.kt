package com.cirogg.deptepicchallenge.api

import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface EpicApi {
    @GET("api/enhanced/all")
    suspend fun getAvailableDates(): Response<MutableList<DatesResponse>>

    @GET("api/enhanced/date/{date}")
    suspend fun getAvailableImages(@Path("date") date: String): Response<ArrayList<ImagesResponse>>

    @Streaming
    @GET ("archive/enhanced/{date}/jpg/{imageName}.jpg")
    suspend  fun getPhoto(@Path("date", encoded = true) date: String, @Path("imageName", encoded = true) jpg: String): Response<ResponseBody?>


}