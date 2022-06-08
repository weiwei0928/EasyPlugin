package zeus.plugin;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

/**
 * 基础的Application
 * Created by huangjian on 2016/6/21.
 */
public class ZeusBaseApplication extends Application {

    //---------------------插件相关的代码-----------------------start
    public ZeusHelper helper = new ZeusHelper();

    @Override
    public Object getSystemService(String name) {
        return helper.getSystemService(this, super.getSystemService(name), name);
    }

    @Override
    public Resources getResources() {//这里需要返回插件框架的resources
        return PluginManager.getResources();
    }

    //会导致pluginmanager的init（）空指针 这么写是有问题的注释掉
//    @Override
//    public Context getBaseContext() {
//        return PluginManager.getContext();
//    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //支持切换语言
        ZeusHelper.onConfigurationChanged();
    }
    //---------------------插件相关的代码-----------------------end
}
