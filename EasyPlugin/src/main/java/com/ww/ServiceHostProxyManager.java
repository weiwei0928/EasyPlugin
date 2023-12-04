
package com.ww;

import android.app.Service;
import android.util.ArrayMap;

import java.util.Map;
import java.util.Vector;

@Deprecated
public enum ServiceHostProxyManager {
    INSTANCE;

    private static final String TAG = "ServiceHostProxyManager";
    // FIXME: 11/3/16 局限性：所有插件共用 10 个 ServiceHostProxy ，若超过插件中的 Service 会启动不了
    public static final int PROXY_SERVICE_COUNT = 10;

    // proxy_service_class_name -> plugin_service_class_name
    private Map<String, String> mProxyServiceClassMap;

    // proxy_service_class_name -> plugin_service_instance
    private Map<String, Service> mProxyServiceInstanceMap;

    private Vector<String> mUsedEmptyServiceCache;

    private static final String TEXT_EMPTY = "";

    ServiceHostProxyManager() {
        mUsedEmptyServiceCache = new Vector<>(PROXY_SERVICE_COUNT);
        mProxyServiceClassMap = new ArrayMap<>(PROXY_SERVICE_COUNT);
        mProxyServiceClassMap.put(ServiceHostProxy.P1.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P2.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P3.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P4.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P5.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P6.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P7.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P8.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P9.class.getName(), TEXT_EMPTY);
        mProxyServiceClassMap.put(ServiceHostProxy.P10.class.getName(), TEXT_EMPTY);

        mProxyServiceInstanceMap = new ArrayMap<>(PROXY_SERVICE_COUNT);
    }

    /**
     * find proxy service name for the given plugin service name
     *
     * @param pluginServiceName Service class name
     * @return proxy service class or null if no available proxy service found
     */
    public String getProxyServiceName(String pluginServiceName) {
        Log.D(TAG,"getProxyServiceName: %s"+pluginServiceName);
        // 1. find proxy service bound with plugin service
        for (Map.Entry<String, String> entry : mProxyServiceClassMap.entrySet()) {
            if (entry.getValue().equals(pluginServiceName)) {
                return entry.getKey();
            }
        }

        // 2. not found, assign a free proxy service class
        for (Map.Entry<String, String> entry : mProxyServiceClassMap.entrySet()) {
            if ((!mUsedEmptyServiceCache.contains(entry.getKey())) && TEXT_EMPTY.equals(entry.getValue())) {
                mUsedEmptyServiceCache.add(entry.getKey());
                return entry.getKey();
            }
        }
        // FIXME: 11/3/16 all proxy service has been used
        // 3. all proxy service are bound with proxy service
        return null;
    }

    public Service getPluginService(String proxyServiceName) {
        Log.D("getPluginService: %s", proxyServiceName);
        return mProxyServiceInstanceMap.get(proxyServiceName);
    }

    public void putPluginService(String proxyServiceName, Service pluginServiceInstance) {
        Log.D(TAG,"putPluginService, proxyServiceName: %s, pluginServiceInstance: %s"+proxyServiceName+
                pluginServiceInstance);
        mProxyServiceClassMap.put(proxyServiceName, pluginServiceInstance.getClass().getName());
        mProxyServiceInstanceMap.put(proxyServiceName, pluginServiceInstance);
        mUsedEmptyServiceCache.remove(proxyServiceName);
    }

    public Service removePluginService(String proxyServiceName) {
        Log.D(TAG,"removePluginService, proxyServiceName: %s"+ proxyServiceName);
        mProxyServiceClassMap.put(proxyServiceName, TEXT_EMPTY);
        mUsedEmptyServiceCache.remove(proxyServiceName);
        return mProxyServiceInstanceMap.remove(proxyServiceName);
    }

    public void dumpProxyServiceClassMap() {
        Log.E(TAG,"============ mProxyServiceClassMap ==============");
        for (Map.Entry<String, String> entry : mProxyServiceClassMap.entrySet()) {
            Log.E("%s -> %s", entry.getKey()+ entry.getValue());
        }
        Log.E(TAG,"=================================================");
    }
}
