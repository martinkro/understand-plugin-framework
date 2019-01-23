package info.loveai.hook;

import android.app.Activity;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class HookHelper {
    public static void attachContext() throws Exception{
        hookActivityThread();
    }

    // mInstrumentation
    private static void hookActivityThread() throws Exception{
        // Get ActivityThread Object
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
        currentActivityThreadMethod.setAccessible(true);
        //currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
        Object currentActivityThread = currentActivityThreadMethod.invoke(null);

        // mInstrumentation Field
        Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

        // Static Proxy
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // Replace
        mInstrumentationField.set(currentActivityThread, evilInstrumentation);
    }

    public static void hookActivity(Activity obj) throws  Exception{
        // mInstrumentation Field
        Field mInstrumentationField = Activity.class.getDeclaredField("mInstrumentation");
        mInstrumentationField.setAccessible(true);
        Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(obj);

        // Static Proxy
        Instrumentation evilInstrumentation = new EvilInstrumentation(mInstrumentation);

        // Replace
        mInstrumentationField.set(obj, evilInstrumentation);
    }


}
