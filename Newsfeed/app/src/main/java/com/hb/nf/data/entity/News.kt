package com.hb.nf.data.entity

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("avatar")
    val avatar: Avatar?,
    @SerializedName("content")
    val content: Any?,
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("document_id")
    val documentId: String,
    @SerializedName("images")
    val images: List<Image>?,
    @SerializedName("origin_url")
    val originUrl: String,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("publisher")
    val publisher: Publisher,
    @SerializedName("title")
    val title: String,
    @SerializedName("sections")
    val sections: ArrayList<Section>
)