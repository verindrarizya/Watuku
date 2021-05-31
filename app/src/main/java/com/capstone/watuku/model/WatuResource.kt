package com.capstone.watuku.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatuResource(
    val title: String? = "",
    var imgUri: String? = "",
    val definition: String? = "",
    val usage: String? = ""
) : Parcelable


