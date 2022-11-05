package com.cirogg.deptepicchallenge.model

import android.os.Parcelable
import com.cirogg.deptepicchallenge.model.response.ImagesResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagesList(
    val imagesList: ArrayList<ImagesResponse>? = arrayListOf()
) : Parcelable