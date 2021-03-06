package com.hb.nf.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import com.hb.nf.ui.detail.DetailActivity
import com.hb.nf.ui.main.MainActivity


object Navigator {

    @SuppressLint("MissingPermission")
    fun callToNumber(activity: Activity, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:$phoneNumber")
        activity.startActivity(intent)
    }


    fun startBrower(activity: Activity, url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(browserIntent)
    }

    fun startMain(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
    }

    fun startDetail(activity: Activity) {
        val intent = Intent(activity, DetailActivity::class.java)
        activity.startActivity(intent)
    }


}