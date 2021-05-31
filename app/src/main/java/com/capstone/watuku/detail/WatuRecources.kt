package com.capstone.watuku.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WatuRecources(
    val title: String? = "",
    val definition: String? = "",
    val usage: String? = "",
    val Img: String? = ""
) : Parcelable


