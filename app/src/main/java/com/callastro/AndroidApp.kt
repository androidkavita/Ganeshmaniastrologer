package com.callastro

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AndroidApp : Application(),LifecycleObserver {
    override fun onCreate() {
        super.onCreate()


        onAppForegrounded()
        onAppBackgrounded()

//        ProcessLifecycleOwner.get().lifecycle.addObserver(this);
//        ProcessLifecycleOwner.get().lifecycle.addObserver(this);
    }
//    override fun onTerminate() {
//        super.onTerminate()
//
//        onAppForegrounded()
//        onAppBackgrounded()
//
//    }





    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
//        toast("False")
//        toast("True")
        Log.d("AppLog", "App in background")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
//        toast("False")
//        toast("True")
        Log.d("AppLog", "App in foreground")
    }

}