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
        plugin_install.setOnClickListener { installPlugin("zeusplugin_test_version2") }
    }

    /**
     * 启动插件
     *
     */
    private fun startPlugin() {
        PluginManager.loadLastVersionPlugin(EasyPluginApplication.PLUGIN_TEST)
        try {

            var className = PluginManager.getPlugin(EasyPluginApplication.PLUGIN_TEST).pluginMeta.mainClass
            Log.d(TAG, "startPlugin: $className")
            val cl: Class<*> = PluginManager.mNowClassLoader.loadClass(className)
            val intent = Intent(this, cl)
            //这种方式为通过在宿主AndroidManifest.xml中预埋activity实现
//            startActivity(intent);
            //这种方式为通过欺骗android系统的activity存在性校验的方式实现
            PluginManager.startActivity(this, intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    /**
     * 安装assets中高版本插件plugin_test_version2.apk
     * 先拷贝到PluginUtil.getZipPath(PluginConfig.PLUGIN_TEST)
     * 然后调用install()安装。
     *
     */
    private fun installPlugin(apkName: String) {
        val zeusPlugin: ZeusPlugin = PluginManager.getPlugin(EasyPluginApplication.PLUGIN_TEST)
        var out: FileOutputStream? = null
        var ins: InputStream? = null
        try {
            val am: AssetManager = PluginManager.mBaseResources.assets
            ins = am.open(apkName)
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(EasyPluginApplication.PLUGIN_TEST))
            out = FileOutputStream(PluginUtil.getZipPath(EasyPluginApplication.PLUGIN_TEST), false)
            val temp = ByteArray(2048)
            var len: Int
            while (ins.read(temp).also { len = it } > 0) {
                out!!.write(temp, 0, len)
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