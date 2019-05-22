package com.hb.nf.data

import com.hb.lib.data.IDataManager
import com.hb.nf.data.entity.User
import com.hb.nf.data.pref.PreferenceHelper
import java.util.*

interface DataManager : IDataManager, PreferenceHelper {

    fun setUser(data: User)

    fun getUser(): User

    fun setBookmarks(data: TreeSet<Double>)

    fun getAllBookmarks(): TreeSet<Double>
}