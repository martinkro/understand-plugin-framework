package info.loveai.sample.hook.binder;

import android.content.ClipData;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

// interface IClipboard
public class BinderHookHandler implements InvocationHandler {
    private static final String TAG = "BinderHookHandler";

    // IInterface service object
    // interface IClipboard extends android.os.IInterface
    private Object mBase;

    public BinderHookHandler(IBinder base, Class<?> stubClass){
        try{
            Method asInterfaceMethod = stubClass.getDeclaredMethod("asInterface",IBinder.class);

            // IClipboard.Stub.asInterface(base);
            mBase = asInterfaceMethod.invoke(null,base);
        }catch(Exception e){
            throw new RuntimeException("BinderHookHandler fail");
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // public ClipData getPrimaryClip ()
        if ("getPrimaryClip".equals(method.getName())) {
            Log.d(TAG, "hook getPrimaryClip");

            return ClipData.newPlainText(null, "you are hooked");
        }

        // public boolean hasPrimaryClip ()
        if ("hasPrimaryClip".equals(method.getName())) {
            return true;
        }

        return method.invoke(mBase, args);
    }
}
