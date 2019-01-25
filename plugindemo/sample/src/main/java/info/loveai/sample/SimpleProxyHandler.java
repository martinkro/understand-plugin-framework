package info.loveai.sample;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SimpleProxyHandler implements InvocationHandler {

    private static final String TAG = "SimpleProxyHandler";
    private Object mBase;
    public SimpleProxyHandler(Object base){
        mBase = base;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.d(TAG,"Method:" + method.getName());
        Log.d(TAG, "Args:" + Arrays.toString(args));
        return method.invoke(mBase,args);
    }
}
