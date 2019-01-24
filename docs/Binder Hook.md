# Binder Hook

Java Hook   Dynamic Proxy

系统服务的获取过程

```Java
// activity
ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
// clipboard
ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
```

```Java
// http://androidxref.com/6.0.1_r10/xref/frameworks/base/core/java/android/content/ContextWrapper.java
public class ContextWrapper extends Context{
    Context mBase;
    
    public ContextWrapper(Context base){
        mBase = base
    }
    
    @Override
    public Object getSystemService(String name){
        return mBase.getSystemService(name);
    }
}

// mBase assign
// http://androidxref.com/6.0.1_r10/xref/frameworks/base/core/java/android/app/ActivityThread.java
public final class ActivityThread{
    private void handleLaunchActivity(ActivityClientRecord r, Intent customIntent) {
        // ...
        Activity a = performLaunchActivity(r, customIntent);
        // ...
    }
    
    private Activity performLaunchActivity(ActivityClientRecord r, Intent customIntent){
        // ...
        if (activity != null) {
            Context appContext = createBaseContextForActivity(r, activity);
            // ...
            activity.attach(
                appContext,
                this,
                getInstrumentation(),
                r.token,
                r.ident,
                app,
                r.intent,
                r.activityInfo,
                title,
                r.parent,
                r.embeddedId,
                r.lastNoConfigurationInstances,
                config,
                r.referrer,
                r.voiceInteractor
            );
            // ...
        }
        // ...
    }
}

// http://androidxref.com/6.0.1_r10/xref/frameworks/base/core/java/android/app/ContextImpl.java
class ContextImpl extends Context{
    // The system service cache for the system services that are cached per-ContextImpl.
    final Object[] mServiceCache = SystemServiceRegistry.createServiceCache();
    
    @Override
    public Object getSystemService(String name){
        return SystemServiceRegistry.getSystemService(this, name);
    }
}

// http://androidxref.com/6.0.1_r10/xref/frameworks/base/core/java/android/app/SystemServiceRegistry.java
final class SystemServiceRegistry {
    private static final HashMap<String, ServiceFetcher<?>> SYSTEM_SERVICE_FETCHERS = 
        new HashMap<String, ServiceFetcher<?>>();
    public static Object getSystemService(ContextImpl ctx, String name){
        ServiceFetcher<?> fetcher = SYSTEM_SERVICE_FETCHERS.get(name);
        return fetcher != null ? fetcher.getService(ctx) : null;
    }
    
    private static <T> void registerService(String serviceName, Class<T> serviceClass,
            ServiceFetcher<T> serviceFetcher) {
        SYSTEM_SERVICE_NAMES.put(serviceClass, serviceName);
        SYSTEM_SERVICE_FETCHERS.put(serviceName, serviceFetcher);
    }
    
    static{
        // ...
        registerService(Context.CLIPBOARD_SERVICE, ClipboardManager.class,
                new CachedServiceFetcher<ClipboardManager>() {
            public ClipboardManager createService(ContextImpl ctx) {
                return new ClipboardManager(ctx.getOuterContext(),ctx.mMainThread.getHandler());
        }});
        // ...
    }
    
    static abstract class CachedServiceFetcher<T> implements ServiceFetcher<T> {
        private final int mCacheIndex;

        public CachedServiceFetcher() {
            mCacheIndex = sServiceCacheSize++;
        }

        @Override
        @SuppressWarnings("unchecked")
        public final T getService(ContextImpl ctx) {
            final Object[] cache = ctx.mServiceCache;
            synchronized (cache) {
                // Fetch or create the service.
                Object service = cache[mCacheIndex];
                if (service == null) {
                    service = createService(ctx);
                    cache[mCacheIndex] = service;
                }
                return (T)service;
            }
        }

        public abstract T createService(ContextImpl ctx);
    }
}
```

# AIDL
IBookStore.aidl
```Java
public interface IBookStore extends android.os.IInterface{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements IBookStore{
    /**
     * Cast an IBinder object into an IBookStore interface,
     * generating a proxy if needed.
     */
    public static IBookStore asInterface(android.os.IBinder obj){
    }
}

private static class Proxy implements IBookStore{
}


}
```