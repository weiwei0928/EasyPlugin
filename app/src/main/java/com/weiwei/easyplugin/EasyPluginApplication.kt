package com.weiwei.easyplugin

import android.app.Application
import android.content.Context
import zeus.plugin.PluginManager
import java.util.HashMap

class EasyPluginApplication : Application() {

    companion object {
        const val PLUGIN_TEST = "zeusplugin_test" //插件测试demo
//        const val PLUGIN_TEST = "app-debug" //插件测试demo
        const val HOTFIX_TEST = "zeushotfix_test" //热修复补丁测试demo
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        val defaultList = HashMap<String, Int>()

        /**
         * apk自带的插件的列表，每次添加内置插件的时候需要添加到这里，格式(pluginName,pluginVersion)
         * pluginVersion一定要与插件中PLUGINWEB_MAINIFEST_FILE文件里的version保持一致。
         * 对于插件还可以使用diff补丁的形式下载增量包，这样可以降低文件下载的大小。
         */
        //补丁必须以EXP_PLUG_HOT_FIX_PREFIX开头
        //插件必须以PluginUtil.EXP_PLUG_PREFIX开头，否则不会识别为插件
        defaultList[PLUGIN_TEST] = 1
        PluginManager.init(this, defaultList)
    }

    override fun onCreate() {
        super.onCreate()
    }
}