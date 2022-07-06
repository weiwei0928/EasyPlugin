package com.ww

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import zeus.plugin.PluginConstant
import zeus.plugin.PluginManager

@SuppressLint("StaticFieldLeak")
object ServiceManager {

    private const val TAG = "ServiceManager"

    private var service: Service? = null

    fun onCreate() {

    }

    fun onStartCommand(intent: Intent?, flags: Int, startId: Int) {
        val bundle = intent?.extras
        val realServiceName = bundle!!.getString(PluginConstant.PLUGIN_REAL_SERVICE)
        if (!TextUtils.isEmpty(realServiceName)) {
            try {
                val pkg = if (intent != null && intent.component != null) intent.component!!
                    .packageName else null
                println("$pkg--->aaa")

                Log.d(TAG, "startPlugin: $realServiceName")
                val cl: Class<*> = PluginManager.mNowClassLoader.loadClass(realServiceName)
                service = cl.newInstance() as Service

                service?.onStartCommand(intent, flags, startId)
            } catch (e: Exception) {
            }
        }

    }

    fun onBind(intent: Intent?) {

    }

    fun onUnbind(intent: Intent?) {

    }

    fun onDestroy() {
        try {
            service?.onDestroy()
            service = null
        } catch (e: Exception) {
        }
    }
}
