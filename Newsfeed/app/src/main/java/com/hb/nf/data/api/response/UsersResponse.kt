package com.hb.nf.data.api.response

import com.google.gson.annotations.SerializedName
import com.hb.nf.data.entity.User


data class UsersResponse(
    @SerializedName("items")
    val data: List<User>,
    @SerializedName("has_more")
    val hasMore: Boolean
)