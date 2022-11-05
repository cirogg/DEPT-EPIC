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

    fun getCleanDate(): String {
        return date?.replace("-", "/")?.split(" ")?.first().toString()
    }

    override fun toString(): String {
        return "Caption=$caption\n Date=$date\n Identifier=$identifier\n Image=$image)"
    }
}