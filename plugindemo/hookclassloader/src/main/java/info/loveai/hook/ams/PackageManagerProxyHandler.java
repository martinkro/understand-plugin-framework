package info.loveai.hook.ams;

import android.content.pm.PackageInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class PackageManagerProxyHandler implements InvocationHandler {
    Object mBase;

    public PackageManagerProxyHandler(Object base){
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getPackageInfo")) {
            return new PackageInfo();
        }
        return method.invoke(mBase, args);
    }
}
