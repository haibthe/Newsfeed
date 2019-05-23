package com.hb.nf.data.entity

import com.google.gson.annotations.SerializedName

data class Publisher(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String
)