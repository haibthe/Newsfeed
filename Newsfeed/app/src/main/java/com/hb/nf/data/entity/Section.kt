package com.hb.nf.data.entity

import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("section_type")
    val type: Int,
    @SerializedName("content")
    val context: Map<String, Any>
)