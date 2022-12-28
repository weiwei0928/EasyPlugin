package zeus.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 基础的activity
 * Created by huangjian on 2016/6/21.
 */
public class ZeusBaseActivity extends AppCompatActivity {

    //---------------------插件相关的代码-----------------------start
    ZeusHelper helper = new ZeusHelper();

    @Override
    public Object getSystemService(String name) {
        return helper.getSystemService(this, super.getSystemService(name), name);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        helper.attachBaseContext(newBase,this);
    }

    @Override
    public Resources getResources() {
        return PluginManager.getResources();
    }

    /**
     * 解决有时插件通过inflate找不到资源的问题
     * @return Resources.Theme
     */
    @Override
    public Resources.Theme getTheme() {
        return helper.getTheme(this);
    }

    /**
     * 给ZeusHelper调用的获取原始theme的方法
     * @return
     */
    public Resources.Theme getSuperTheme() {
        return super.getTheme();
    }

    @Override
    public void startActivity(Intent intent) {
        PluginManager.startActivity(intent);
    }

    @Nullable
    @Override
    public ComponentName startService(Intent intent) {
        return PluginManager.startService(intent);
    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        return super.bindService(service, conn, flags);
    }
}
