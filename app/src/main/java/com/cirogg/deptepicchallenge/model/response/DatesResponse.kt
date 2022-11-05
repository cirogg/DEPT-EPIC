package com.cirogg.deptepicchallenge.model.response

import android.os.Parcelable
import com.cirogg.deptepicchallenge.model.FetchStatus
import kotlinx.android.parcel.Parcelize

@Parcelize
class DatesResponse(
    var date: String,
    var dateImages: ArrayList<ImagesResponse>? = null,
    var downStatus: FetchStatus? = null
) : Parcelable{
}