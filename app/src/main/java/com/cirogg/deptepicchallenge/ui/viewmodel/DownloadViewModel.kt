package com.cirogg.deptepicchallenge.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cirogg.deptepicchallenge.model.ImagesList
import com.cirogg.deptepicchallenge.model.response.DatesResponse
import com.cirogg.deptepicchallenge.repository.DownloadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DownloadViewModel(
    private val repository: DownloadRepository
) : ViewModel() {

    val totalImagesCounter = MutableLiveData<Int>().apply { value = 0 }

    fun downloadImage(imagesList: ImagesList, context: Context) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imagesList.imagesList?.let {
                    it.let { it ->
                        for (imagesResponse in it) {
                            repository.downloadAndSave(
                                imagesResponse.getCleanDate(),
                                imagesResponse.image!!
                            ).collect{
                                repository.saveFile(it, imagesResponse.getCleanDate(), imagesResponse.image,context)
                                Log.d("DownloadViewModel", "downloadImage: ${imagesResponse.image}")
                                totalImagesCounter.postValue(totalImagesCounter.value!! + 1)
                            }
                        }
                    }
                }
            }
        }
    }

}