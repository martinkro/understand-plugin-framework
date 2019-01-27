package info.loveai.sample;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import info.loveai.hook.AMSHookHelper;
import info.loveai.sample.hook.binder.BinderHookHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    TextView mTextViewInfo;
    EditText mEditTextInfo;

    private static final String TAG = "MainActivity";

    @Override
    protected void attachBaseContext(Context newBase) {
        //HookHelper.hookActivityManager();
        //HookHelper.hookPackageManager(newBase);
        //AMSHookHelper.hookActivityManagerNative();
        //AMSHookHelper.hookActivityThreadHandler();
        super.attachBaseContext(newBase);
        try {
            AMSHookHelper.hookActivityManagerNative();
            AMSHookHelper.hookActivityThreadHandler();
        } catch (Throwable throwable) {
            throw new RuntimeException("hook failed", throwable);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        try {
//            BinderHookHelper.hookClipboardService();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        setContentView(R.layout.activity_main);

        mTextViewInfo = findViewById(R.id.tv_info);
        mEditTextInfo = findViewById(R.id.et_info);
        findViewById(R.id.bt_binder_hook).setOnClickListener(this);
        findViewById(R.id.bt_test_ams).setOnClickListener(this);
        findViewById(R.id.bt_test_pms).setOnClickListener(this);
        findViewById(R.id.bt_start_target_activity).setOnClickListener(this);
        findViewById(R.id.bt_get_pkg_list).setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.bt_binder_hook:
                break;
            case R.id.bt_test_ams:
                Uri uri = Uri.parse("https://wwww.baidu.com");
                Intent t = new Intent(Intent.ACTION_VIEW);
                t.setData(uri);
                startActivity(t);
                break;
            case R.id.bt_test_pms:
                getPackageManager().getInstalledApplications(0);
                break;
            case R.id.bt_get_pkg_list:
                getPackageList();
                break;
            case R.id.bt_start_target_activity:
                startActivity(new Intent(MainActivity.this, TargetActivity.class));
                break;
            default:
                break;
        }
    }

    public void getPackageList(){
        Log.d(TAG,"===>Use getInstalledApplications");
        List<ApplicationInfo> listAppInfo = getPackageManager().getInstalledApplications(0);
        for(ApplicationInfo app:listAppInfo){
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM)  == 0){
                Log.d(TAG,"package name:" + app.packageName);
            }
        }

        Log.d(TAG,"===>Use queryIntentActivities");
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setAction(Intent.ACTION_MAIN);
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(intent, 0);
        for(ResolveInfo info:resolveInfos){
            if ((info.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0){
                String packageName = info.activityInfo.applicationInfo.packageName;
                Log.d(TAG,"package name:" + packageName);
            }

        }

        List<PermissionGroupInfo> groupInfoList =getPackageManager().getAllPermissionGroups(0);
        for(PermissionGroupInfo info:groupInfoList){
            //Log.d(TAG,"PermissionGroup name:" + info.name);
        }
        Log.d(TAG,"===>Use queryPermissionsByGroup");
        try {
            List<PermissionInfo> infos = getPackageManager().queryPermissionsByGroup("android.permission-group.STORAGE", 0);
            for(PermissionInfo info:infos){
                String packageName = info.packageName;
                Log.d(TAG,"package name:" + packageName);
            }
        }catch (Exception e){

        }

    }

    public void testGetSystemService(){
        ClipboardManager cm = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
    }
}
