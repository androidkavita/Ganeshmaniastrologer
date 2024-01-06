package com.maxtra.astrorahiastrologer.util

import android.content.Context
import android.net.ConnectivityManager

object androidextentions {
     private lateinit var mContext: Context
    operator fun invoke(applicationContext: Context?) {
       mContext=applicationContext!!
    }
    fun phoneIsOnline(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}