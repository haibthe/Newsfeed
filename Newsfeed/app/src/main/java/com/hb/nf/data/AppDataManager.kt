package com.hb.nf.data

import android.content.Context
import com.hb.nf.data.cache.ICache
import com.hb.nf.data.entity.News
import com.hb.nf.data.pref.PreferenceHelper


class AppDataManager
constructor(
    private val context: Context,
    private val pref: PreferenceHelper,
    private val cache: ICache
) : DataManager {

    companion object {
    }

    private var mNews: News? = null


    override fun setNews(data: News) {
        mNews = data
    }

    override fun getNews(): News {
        return mNews!!
    }

}