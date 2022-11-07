package com.cirogg.deptepicchallenge.repository

import android.util.Log
import com.cirogg.deptepicchallenge.api.EpicApi
import com.cirogg.deptepicchallenge.api.RetrofitInstance
import com.cirogg.deptepicchallenge.model.FetchStatus
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.FileOutputStream
import java.io.InputStream

class DatesRepository(
    private val retrofitInstance: RetrofitInstance
) {

    fun getAvailableDates(): Flow<MutableList<DatesResponse>> = flow {
        val call = retrofitInstance.epicApi.getAvailableDates()
        val dates = call.body()
        if (call.isSuccessful) {
            emit(dates!!)
        } else {
            emit(mutableListOf())
        }
    }

    fun getImagesByDate(date: String): Flow<DatesResponse> = flow {
        val call = retrofitInstance.epicApi.getAvailableImages(date)
        val images = call.body()
        if (call.isSuccessful) {
            emit(handlePhotosResponse(date, call))
        } else {
            emit(DatesResponse(date, null,FetchStatus.ERROR))
        }
    }

    private fun handlePhotosResponse(
        date: String,
        imagesResponse: Response<ArrayList<ImagesResponse>>
    ): DatesResponse {
        if (imagesResponse.isSuccessful) {
            imagesResponse.body()?.let { resultResponse ->
                if (resultResponse.isNotEmpty()) {
                    return DatesResponse(date, imagesResponse.body(),FetchStatus.READY)
                }else{
                    return DatesResponse(date, null,FetchStatus.ERROR)
                }
            }
        }
        return DatesResponse(date, null,FetchStatus.ERROR)
    }

}