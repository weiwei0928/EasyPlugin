package com.ww.easyplugin.plugin

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ww.easyplugin.EasyPluginApplication
import com.ww.easyplugin.R
import kotlinx.android.synthetic.main.activity_plugin.*
import zeus.plugin.PluginManager
import zeus.plugin.PluginUtil
import zeus.plugin.ZeusBaseActivity
import zeus.plugin.ZeusPlugin
import java.io.FileOutputStream
import java.io.InputStream

/**
 *
 * @author weiwei
 * Created on 12/17/21 8:59 PM
 */

class TestPluginActivity : ZeusBaseActivity() {

    companion object{
        private const val TAG = "TestPluginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plugin)
        title = "插件测试"

        plugin_test.setOnClickListener { startPlugin() }
        plugin_update.setOnClickListener { installPlugin("plugin_sample1_version2.apk") }
    }

    /**
     * 启动插件
     *
     */
    private fun startPlugin() {
        PluginManager.loadLastVersionPlugin(EasyPluginApplication.PLUGIN_SAMPLE1)
        try {

            var className = PluginManager.getPlugin(EasyPluginApplication.PLUGIN_SAMPLE1).pluginMeta.mainClass
            Log.d(TAG, "startPlugin: $className")
            val cl: Class<*> = PluginManager.mNowClassLoader.loadClass(className)
            val intent = Intent(this, cl)
            startActivity(intent);

        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 安装assets中高版本插件plugin_test_version2.apk
     * 先拷贝到PluginUtil.getZipPath(PluginConfig.PLUGIN_SAMPLE1)
     * 然后调用install()安装。
     *
     */
    private fun installPlugin(apkName: String) {
        val zeusPlugin: ZeusPlugin = PluginManager.getPlugin(EasyPluginApplication.PLUGIN_SAMPLE1)
        var out: FileOutputStream? = null
        var ins: InputStream? = null
        try {
            val am: AssetManager = PluginManager.mBaseResources.assets
            ins = am.open(apkName)
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(EasyPluginApplication.PLUGIN_SAMPLE1))
            out = FileOutputStream(PluginUtil.getZipPath(EasyPluginApplication.PLUGIN_SAMPLE1), false)
            val temp = ByteArray(2048)
            var len: Int
            while (ins.read(temp).also { len = it } > 0) {
                out?.write(temp, 0, len)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            PluginUtil.close(ins)
            PluginUtil.close(out)
        }
        val installed: Boolean = zeusPlugin.install()
        if (installed) {
            Toast.makeText(PluginManager.mBaseContext, "高版本插件安装成功", Toast.LENGTH_SHORT).show()
        }
    }
}