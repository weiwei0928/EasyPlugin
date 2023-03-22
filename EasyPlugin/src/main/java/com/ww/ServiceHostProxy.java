
package com.ww;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.HashMap;

import zeus.plugin.PluginConstant;
import zeus.plugin.PluginManager;

/** 宿主中用于占坑的 Service 代理基类 */
abstract class ServiceHostProxy extends Service {

  private static final String TAG = "ServiceHostProxy";
  public static final String ORIGIN_INTENT = "origin_intent";
  private Service mPluginService;

  @Override
  public void onCreate() {
    super.onCreate();
    Log.D(TAG, "onCreate E");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.D(TAG, "onStartCommand, intent: %s" + intent);

    if (intent == null) {
      final String msg = "onStartCommand intent is null";
      Log.E(TAG, msg);

      return START_STICKY;
    }

//    Intent targetIntent = intent.getParcelableExtra(ORIGIN_INTENT);
//    if (targetIntent == null) {
//      final String msg = "onStartCommand targetIntent is null";
//      Log.E(TAG, msg);
//
//      return START_STICKY;
//    }

    String serviceClassName = intent.getExtras().getString(PluginConstant.PLUGIN_REAL_SERVICE);

    if (mPluginService == null) {
      try {
        mPluginService = handleCreateService(serviceClassName);
        Log.D(TAG, "onStartCommand handleCreateService ok");
      } catch (Throwable throwable) {
        final String msg = "onStartCommand handleCreateService error";
        Log.E(TAG, throwable + msg);

        return START_STICKY;
      }
    }

//    final PluginClassLoader pluginClassLoader = targetInfo.getPluginClassLoader();
//    if (pluginClassLoader != null) {
//      targetIntent.setExtrasClassLoader(pluginClassLoader);
//    } else {
//      Log.E(TAG, "onStartCommand, pluginClassLoader is null !!!");
//    }

    final int ret = mPluginService.onStartCommand(intent, flags, startId);

    return ret;
  }

  private Service handleCreateService(String serviceClassName)
      throws Throwable {

    final ClassLoader pluginClassLoader = PluginManager.mNowClassLoader;


    Class<?> serviceClass = pluginClassLoader.loadClass(serviceClassName);
    if (serviceClass == null) {
      throw new IllegalStateException("PluginClassLoader#loadClass return null");
    }

    Log.D(TAG, "handleCreateService: "+serviceClass);
    Service service = (Service) serviceClass.newInstance();

    //处理intentService
//    if (PluginInterceptService.class.isAssignableFrom(serviceClass)) {
//      final PluginInterceptService pluginInterceptService = (PluginInterceptService) service;
//      pluginInterceptService.setContextProxy(new ContextProxy<Service>(pluginInfo, this));
//      pluginInterceptService.attachBaseContext(getBaseContext());
//    } else if (PluginInterceptIntentService.class.isAssignableFrom(serviceClass)) {
//      final PluginInterceptIntentService pluginInterceptIntentService =
//          (PluginInterceptIntentService) service;
//      pluginInterceptIntentService.setContextProxy(new ContextProxy<Service>(pluginInfo, this));
//      pluginInterceptIntentService.attachBaseContext(getBaseContext());
//    }

    service.onCreate();

    ServiceHostProxyManager.INSTANCE.putPluginService(getClass().getName(), service);

    return service;
  }

  @Override
  public IBinder onBind(Intent intent) {
    Log.D(TAG, "onBind, intent: %s" + intent);

    if (intent == null) {
      final String msg = "onBind intent is null";
      Log.E(TAG, msg);

      return null;
    }

//    Intent targetIntent = intent.getParcelableExtra(ORIGIN_INTENT);
//    if (targetIntent == null) {
//      final String msg = "onBind targetIntent is null";
//      Log.E(TAG, msg);
//
//      return null;
//    }

    String serviceClassName = intent.getComponent().getClassName();

    if (mPluginService == null) {
      try {
        mPluginService = handleCreateService(serviceClassName);
        Log.D(TAG, "onBind handleCreateService ok");
      } catch (Throwable throwable) {
        final String msg = "onBind handleCreateService error";
        Log.E(TAG, throwable + msg);

        return null;
      }
    }

    final IBinder iBinder = mPluginService.onBind(intent);

    return iBinder;
  }

  @Override
  public boolean onUnbind(Intent intent) {
    Log.D(TAG, "onUnbind, intent: %s" + intent);

    if (intent == null) {
      final String msg = "onUnbind intent is null";
      Log.E(TAG, msg);

      return false;
    }

    Intent targetIntent = intent.getParcelableExtra(ORIGIN_INTENT);
    if (targetIntent == null) {
      final String msg = "onUnbind targetIntent is null";
      Log.E(TAG, msg);

      return false;
    }

    // FIXME check target service class name?
    String serviceClassName = targetIntent.getComponent().getClassName();

    if (mPluginService == null) {
      final String msg = "onUnbind service is null";
      Log.E(TAG, msg);

      return false;
    }

    final boolean result = mPluginService.onUnbind(intent);

    return result;
  }

  @Override
  public void onRebind(Intent intent) {
    super.onRebind(intent);
    Log.D(TAG, "onRebind, intent: %s" + intent);

    if (intent == null) {
      final String msg = "onRebind intent is null";
      Log.E(TAG, msg);

      return;
    }

//    Intent targetIntent = intent.getParcelableExtra(ORIGIN_INTENT);
//    if (targetIntent == null) {
//      final String msg = "onRebind targetIntent is null";
//      Log.E(TAG, msg);
//
//      return;
//    }

    // FIXME check target service class name?
    String serviceClassName = intent.getComponent().getClassName();

    if (mPluginService == null) {
      final String msg = "onRebind service is null";
      Log.E(TAG, msg);

      return;
    }

    mPluginService.onRebind(intent);
  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    super.onTaskRemoved(rootIntent);
    Log.D(TAG, "onTaskRemoved, intent: %s" + rootIntent);
  }

  @SuppressWarnings("PMD.CallSuperLast")
  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.D(TAG, "onDestroy");

    HashMap<String, Object> params = new HashMap<>();

    if (mPluginService == null) {
      final String msg = "onDestroy service is null";
      Log.E(TAG, msg);

      return;
    }

    mPluginService.onDestroy();
    mPluginService = null;
    ServiceHostProxyManager.INSTANCE.removePluginService(getClass().getName());
  }

  public static class P1 extends ServiceHostProxy {}

  public static class P2 extends ServiceHostProxy {}

  public static class P3 extends ServiceHostProxy {}

  public static class P4 extends ServiceHostProxy {}

  public static class P5 extends ServiceHostProxy {}

  public static class P6 extends ServiceHostProxy {}

  public static class P7 extends ServiceHostProxy {}

  public static class P8 extends ServiceHostProxy {}

  public static class P9 extends ServiceHostProxy {}

  public static class P10 extends ServiceHostProxy {}
}
