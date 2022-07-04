package com.ww.easyplugin

import android.content.Intent
import android.content.res.AssetManager
import android.os.Bundle
import android.os.Process
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ww.easyplugin.androidhotfix.TestHotfixActivity1
import com.ww.easyplugin.plugin.TestPluginActivity
import zeus.plugin.PluginManager
import zeus.plugin.PluginUtil
import zeus.plugin.ZeusPlugin
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    /**
     * 插件测试
     *
     * @param view
     */
    fun testPlugin(view: View?) {
        val intent = Intent(this@MainActivity, TestPluginActivity::class.java)
        startActivity(intent)
    }

    /**
     * 补丁测试
     *
     * @param view
     */
    fun testHotfix(view: View?) {
        //第一次启动是宿主，应用补丁后就是补丁。
        val intent = Intent(this@MainActivity, TestHotfixActivity1::class.java)
        startActivity(intent)
    }

    /**
     * 应用补丁
     *
     * @param view
     */
    fun applyHotfix(view: View?) {
        if (PluginManager.isInstall(EasyPluginApplication.HOTFIX_TEST)) {
            Toast.makeText(
                this,
                "补丁" + EasyPluginApplication.HOTFIX_TEST + "已经被安装,不用再次安装",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val zeusPlugin: ZeusPlugin = PluginManager.getPlugin(EasyPluginApplication.HOTFIX_TEST)
        var out: FileOutputStream? = null
        var ins: InputStream? = null
        try {
            val am: AssetManager = PluginManager.mBaseResources.assets
            ins = am.open("zeushotfix_test.apk")
            PluginUtil.createDirWithFile(PluginUtil.getZipPath(EasyPluginApplication.HOTFIX_TEST))
            out = FileOutputStream(PluginUtil.getZipPath(EasyPluginApplication.HOTFIX_TEST), false)
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
        val result: Boolean = zeusPlugin.install()
        if (result) {
            Toast.makeText(
                this,
                "补丁" + EasyPluginApplication.HOTFIX_TEST + "安装成功,下次启动生效",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Process.killProcess(Process.myPid())
    }
}