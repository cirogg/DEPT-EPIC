package com.cirogg.deptepicchallenge.repository

import com.cirogg.deptepicchallenge.api.EpicApi
import com.cirogg.deptepicchallenge.api.RetrofitInstance
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

}