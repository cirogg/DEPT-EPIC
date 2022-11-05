package com.cirogg.deptepicchallenge.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ImagesResponse(
    val caption: String?,
    val date: String?,
    val identifier: String?,
    val image: String?
) : Parcelable {
}