package com.weiwei.easyplugin.plugin

import android.app.Service
import android.content.Intent

import android.os.IBinder
import android.util.Log


class StubService : Service() {

//    private val serviceManager : Lazy<ServiceManager> = lazy { ServiceManager() }



    override fun onCreate() {
        super.onCreate()
        Log.d("StubService", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StubService", "onStartCommand")
        ServiceManager.onStartCommand(intent, flags, startId)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        ServiceManager.onDestroy()
        Log.d("StubService", "onDestroy")
    }
}