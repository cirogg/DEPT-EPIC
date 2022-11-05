package com.cirogg.deptepicchallenge.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlList(val listOfUrls: List<String>) : Parcelable