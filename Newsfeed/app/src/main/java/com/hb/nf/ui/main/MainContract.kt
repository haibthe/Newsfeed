package com.hb.nf.ui.main

import com.hb.nf.data.entity.News

interface MainContract {
    interface View {
        fun loadBy(type: String)

        fun updateTablayout(data: Array<String>)
    }

    interface Presenter {
        fun loadNextPage()

        fun setNewsSelected(news: News)

        fun filter(name: String)


    }
}