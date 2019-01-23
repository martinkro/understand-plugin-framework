package info.loveai.sample.hook.binder;

import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

// Hook IBinder Object
public class ClipboardProxyHandler implements InvocationHandler {
    private static final String TAG = "ClipboardProxyHandler";

    // Real Binder Object
    private IBinder mBase;
    private  Class<?> mStub;
    private Class<?> mIInterface;

    public ClipboardProxyHandler(IBinder base){
        mBase = base;
        try{
            mStub = Class.forName("android.content.IClipboard$Stub");
            mIInterface = Class.forName("android.content.IClipboard");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("queryLocalInterface".equals(method.getName())) {
            Log.d(TAG, "hook queryLocalInterface");

            return Proxy.newProxyInstance(
                    proxy.getClass().getClassLoader(),
                    new Class[]{mIInterface},
                    new BinderHookHandler(mBase,mStub)
                    );
        }
        Log.d(TAG, "method:" + method.getName());
        return method.invoke(mBase, args);
    }
}
