package com.hb.nf.data.repository

import com.hb.nf.data.entity.Reputation
import com.hb.nf.data.entity.User
import com.hb.nf.data.entity.dw.DataWrapper
import io.reactivex.Observable

/**
 * Created by buihai on 9/9/17.
 */

interface SystemRepository {

    fun getDataTest(): Observable<List<DataWrapper<*>>>

    fun getUsers(page: Int = 1) : Observable<List<DataWrapper<User>>>

    fun getReputation(user: User, page: Int = 1): Observable<List<DataWrapper<Reputation>>>
}
