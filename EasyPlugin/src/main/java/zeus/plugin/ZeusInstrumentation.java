package zeus.plugin;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @author ww
 */
public class ZeusInstrumentation extends Instrumentation {

//    private Instrumentation mInstrumentation;
//    private PackageManager mPackageManager;
//
//    public ZeusInstrumentation(Instrumentation instrumentation,PackageManager packageManager) {
//        mInstrumentation = instrumentation;
//        mPackageManager = packageManager;
//    }


    //尽量不使用这种hook系统正常流程的方式启动,有风险。我们使用gradle插件字节码替换所有activity没必要使用这个方法了
//    @Deprecated
//    public ActivityResult execStartActivity(
//            Context who, IBinder contextThread, IBinder token, Activity target,
//            Intent intent, int requestCode, Bundle options) {
//
//        List<ResolveInfo> resolveInfo = mPackageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL);
//        //判断启动的插件Activity是否在AndroidManifest.xml中注册过
//        if (null == resolveInfo || resolveInfo.size() == 0) {
//            //保存目标插件
//            intent.putExtra(PluginConstant.PLUGIN_REAL_ACTIVITY, intent.getComponent().getClassName());
//            //设置为占坑Activity
//            intent.setClassName(intent.getComponent().getPackageName(), PluginConstant.PLUGIN_ACTIVITY_FOR_STANDARD);
//        }
//
//        try {
//            Method execStartActivity = Instrumentation.class.getDeclaredMethod("execStartActivity",
//                    Context.class, IBinder.class, IBinder.class, Activity.class,
//                    Intent.class, int.class, Bundle.class);
//            return (ActivityResult) execStartActivity.invoke(mInstrumentation, who, contextThread, token, target, intent, requestCode, options);
//        } catch (NoSuchMethodException e) {
//            Log.E("printStackTrace",e.toString());
//        } catch (IllegalAccessException e) {
//            Log.E("printStackTrace",e.toString());
//        } catch (InvocationTargetException e) {
//            Log.E("printStackTrace",e.toString());
//        }
//        return null;
//    }


    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //给Bundle设置classLoader以使Bundle中序列化对象可以直接转化为插件中的对象
                //类似于在宿主中这么使用:TestInPlugin testInPlugin = (TestInPlugin)bundle.get("TestInPlugin");
                //TestInPlugin是在插件中定义的,如果不这么设置则会找不到TestInPlugin类
                bundle.setClassLoader(PluginManager.mNowClassLoader);
                if ("com.zeus.ZeusActivityForStandard".equals(className)) {
                    String realActivity = bundle.getString(PluginConstant.PLUGIN_REAL_ACTIVITY);
                    if (!TextUtils.isEmpty(realActivity)) {
                        cl = PluginManager.mNowClassLoader;
                        String pkg = intent != null && intent.getComponent() != null
                                ? intent.getComponent().getPackageName() : null;
//                        return getFactory(pkg).instantiateActivity(cl, className, intent);
                        return super.newActivity(cl, realActivity, intent);
                    }
                }
            }
        }

        return super.newActivity(cl, className, intent);
    }

}
