package info.loveai.hookclassloader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

import info.loveai.hook.ams.AMSHookHelper;
import info.loveai.hook.classloader.BaseDexClassLoaderHookHelper;
import info.loveai.hook.classloader.LoadedApkClassLoaderHookHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    private static final int PATCH_BASE_CLASS_LOADER = 1;  // VirtualApp Single ClassLoader

    // TODO  MultiClassLoader  Problem!!!
    private static final int CUSTOM_CLASS_LOADER = 2;   // DroidPlugin  MultiClassLoader

    private static final int HOOK_METHOD = 1; //CUSTOM_CLASS_LOADER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt_classloader_path).setOnClickListener(this);

        Log.d(TAG, "context classloader: " + getApplicationContext().getClassLoader());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            Utils.extractAssets(newBase, "hook-dynamic-proxy.apk");
            Utils.extractAssets(newBase, "hook-ams-pms.apk");
            Utils.extractAssets(newBase, "test.apk");

            if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {

                File dexFile = getFileStreamPath("test.apk");
                File optDexFile = getFileStreamPath("test.dex");
                BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), dexFile, optDexFile);
            } else {
                LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(getFileStreamPath("hook-ams-pms.apk"));
            }

            AMSHookHelper.hookActivityManagerNative();
            AMSHookHelper.hookActivityThreadHandler();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.bt_classloader_path:
                onClassLoaderPath();
                break;
            default:
                break;
        }
    }

    private void onClassLoaderPath(){
        try {
            Intent t = new Intent();
            if (HOOK_METHOD == PATCH_BASE_CLASS_LOADER) {
                t.setComponent(new ComponentName(
                        "info.loveai.vpp.test",
                        "info.loveai.vpp.test.MainActivity"));
            } else {
                t.setComponent(new ComponentName(
                        "info.loevai.vpp.hamspms",
                        "info.loevai.vpp.hamspms.MainActivity"));
            }
            startActivity(t);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
