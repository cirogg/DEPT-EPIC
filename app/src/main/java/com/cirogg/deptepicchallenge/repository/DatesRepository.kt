package com.cirogg.deptepicchallenge.repository

import com.cirogg.deptepicchallenge.api.EpicApi
import com.cirogg.deptepicchallenge.api.RetrofitInstance
import com.cirogg.deptepicchallenge.model.FetchStatus
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class DatesRepository(
    private val epicApi: EpicApi
) {

    fun getAvailableDates(): Flow<MutableList<DatesResponse>> = flow {
        val call = RetrofitInstance.epicApi.getAvailableDates()
        val dates = call.body()
        if (call.isSuccessful) {
            emit(dates!!)
        } else {
            emit(mutableListOf())
        }
    }

    fun getImagesByDate(date: String): Flow<DatesResponse> = flow {
        val call = RetrofitInstance.epicApi.getAvailableImages(date)
        val images = call.body()
        if (call.isSuccessful) {
            emit(handlePhotosResponse(date, call))
        } else {
            emit(DatesResponse(date, null,FetchStatus.ERROR))
        }
    }

    private fun handlePhotosResponse(
        date: String,
        availablePhotosResponse: Response<ArrayList<ImagesResponse>>
    ): DatesResponse {
        if (availablePhotosResponse.isSuccessful) {
            availablePhotosResponse.body()?.let { resultResponse ->
                if (resultResponse.isNotEmpty()) {
                    return DatesResponse(date, availablePhotosResponse.body(),FetchStatus.READY)
                }else{
                    return DatesResponse(date, null,FetchStatus.ERROR)
                }
            }
        }
        return DatesResponse(date, null,FetchStatus.ERROR)
    }

}