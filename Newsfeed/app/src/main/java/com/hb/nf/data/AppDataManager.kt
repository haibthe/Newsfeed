package com.hb.nf.data

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.hb.nf.data.cache.ICache
import com.hb.nf.data.entity.User
import com.hb.nf.data.pref.PreferenceHelper
import java.util.*


class AppDataManager
constructor(
    private val context: Context,
    private val pref: PreferenceHelper,
    private val cache: ICache
) : DataManager {

    companion object {
        const val BOOKMARK_TAG = "BOOKMARK"
    }

    private var mUser: User? = null


    override fun setUser(data: User) {
        mUser = data
    }

    override fun getUser(): User {
        return mUser!!
    }

    override fun setBookmarks(data: TreeSet<Double>) {
        cache.delete(BOOKMARK_TAG)
        cache.setObject(BOOKMARK_TAG, data)
    }

    override fun getAllBookmarks(): TreeSet<Double> {
        var data = cache.getObject<TreeSet<Double>>(BOOKMARK_TAG, object : TypeToken<TreeSet<Double>>() {}.rawType)
        if (data == null) data = TreeSet()
        return data
    }
}