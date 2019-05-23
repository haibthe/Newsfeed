package com.hb.nf.data.entity

import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("height")
    val height: Int,
    @SerializedName("href")
    val href: String,
    @SerializedName("main_color")
    val mainColor: String,
    @SerializedName("width")
    val width: Int
)