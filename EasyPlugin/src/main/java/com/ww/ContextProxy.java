
package com.ww;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import zeus.plugin.PluginManager;

class ContextProxy<T extends Context> {

  private final T mContext;

  ContextProxy(T context) {

    this.mContext = context;
  }

  public T getContext() {
    return mContext;
  }

  AssetManager getAssets() {

    return PluginManager.mBaseContext.getAssets();
  }

  public Resources getResources() {
    return PluginManager.mNowResources;
  }

  Context getApplicationContext() {
    return PluginManager.mBaseContext.getApplicationContext();
  }

  ClassLoader getClassLoader() {
    return PluginManager.mNowClassLoader;
  }

  ApplicationInfo getApplicationInfo() {
    return PluginManager.mBaseContext.getApplicationInfo();
  }

  //  @SuppressWarnings("PMD.UseVarargs")
  //  Intent[] setIntentExtra(Intent[] intents) {
  //    int size = intents.length;
  //    Intent[] replacedIntents = new Intent[size];
  //    for (int i = 0; i < size; i++) {
  //      replacedIntents[i] = setActivityIntentExtra(intents[i]);
  //    }
  //
  //    return replacedIntents;
  //  }

  //  Intent setActivityIntentExtra(Intent intent) {
  //    intent.putExtra(Constants.EXTRA_FROM_PLUGIN, mPluginInfo.packageName);
  //    return IntentUtils.wrapToActivityHostProxyIntentIfNeeded(intent);
  //  }

  //  Intent setServiceIntentExtra(Intent intent) {
  //    intent.putExtra(Constants.EXTRA_FROM_PLUGIN, mPluginInfo.packageName);
  //    return IntentUtils.wrapToServiceHostProxyIntentIfNeeded(intent);
  //  }

  public void startActivity(Intent intent) {
    mContext.startActivity(intent);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  public void startActivity(Intent intent, @Nullable Bundle options) {
    mContext.startActivity(intent, options);
  }

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
  void startActivities(Intent[] intents, @Nullable Bundle options) {
    mContext.startActivities(intents, options);
  }

  void startActivities(Intent... intents) {
    mContext.startActivities(intents);
  }

  public ComponentName startService(Intent service) {
    return mContext.startService(service);
  }

  boolean stopService(Intent name) {
    return mContext.stopService(name);
  }

  boolean bindService(Intent service, ServiceConnection conn, int flags) {
    return mContext.bindService(service, conn, flags);
  }
}
