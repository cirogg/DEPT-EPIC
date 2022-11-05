package com.cirogg.deptepicchallenge

import com.cirogg.deptepicchallenge.api.EpicApi
import com.cirogg.deptepicchallenge.repository.DatesRepository
import com.cirogg.deptepicchallenge.ui.viewmodel.DatesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val epicAppModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://epic.gsfc.nasa.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EpicApi::class.java)
    }

    single {
        DatesRepository(get())
    }

    viewModel {
        DatesViewModel(get())
    }
}