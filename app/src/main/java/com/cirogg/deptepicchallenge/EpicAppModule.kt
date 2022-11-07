package com.cirogg.deptepicchallenge

import com.cirogg.deptepicchallenge.api.EpicApi
import com.cirogg.deptepicchallenge.api.RetrofitInstance
import com.cirogg.deptepicchallenge.repository.DatesRepository
import com.cirogg.deptepicchallenge.repository.DownloadRepository
import com.cirogg.deptepicchallenge.ui.viewmodel.DatesViewModel
import com.cirogg.deptepicchallenge.ui.viewmodel.DownloadViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val epicAppModule = module {

    single {
        RetrofitInstance()
    }

    single {
        DatesRepository(get())
    }
    single {
        DownloadRepository(get())
    }

    viewModel {
        DatesViewModel(get())
    }
    viewModel {
        DownloadViewModel(get())
    }
}