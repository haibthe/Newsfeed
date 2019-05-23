package com.hb.nf.data.repository

import com.hb.nf.data.entity.News
import com.hb.nf.data.entity.dw.DataWrapper
import io.reactivex.Observable

/**
 * Created by buihai on 9/9/17.
 */

interface SystemRepository {

    fun getDataTest(): Observable<List<DataWrapper<*>>>

    fun getNewsfeed(page: Int = 1) : Observable<List<DataWrapper<News>>>

    fun getDetail() : Observable<News>
}
