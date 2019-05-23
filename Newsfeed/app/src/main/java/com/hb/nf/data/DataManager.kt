package com.hb.nf.data

import com.hb.lib.data.IDataManager
import com.hb.nf.data.entity.News
import com.hb.nf.data.pref.PreferenceHelper

interface DataManager : IDataManager, PreferenceHelper {

    fun setNews(data: News)

    fun getNews(): News

}