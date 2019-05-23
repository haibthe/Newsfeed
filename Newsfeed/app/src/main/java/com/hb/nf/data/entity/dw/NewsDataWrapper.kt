package com.hb.nf.data.entity.dw

import com.hb.nf.data.entity.News

class NewsDataWrapper(news: News) : DataWrapper<News>(news) {

    private val data: News
        get() {
            return getData()
        }

    override fun getTitle(): String {
        return data.title
    }

    override fun getSubtitle(): String {
        return data.contentType
    }

    override fun getDescription(): String {
        return data.description
    }

    override fun getIcon(): String {
        if (data.avatar != null) {
            return data.avatar!!.href
        }
        return ""

    }
}
