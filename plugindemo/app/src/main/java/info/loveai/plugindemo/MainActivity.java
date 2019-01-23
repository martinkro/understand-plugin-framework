package info.loveai.plugindemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import info.loveai.hook.HookHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextViewHello;
    private TextView mTextViewInfo;
    private EditText mEditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            HookHelper.hookActivity(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTextViewHello = findViewById(R.id.tv_hello);
        mTextViewInfo = findViewById(R.id.tv_info);
        mEditTextName = findViewById(R.id.et_name);

        findViewById(R.id.bt_say_hello).setOnClickListener(this);
        findViewById(R.id.bt_static_proxy).setOnClickListener(this);
        findViewById(R.id.bt_dynamic_proxy).setOnClickListener(this);
        findViewById(R.id.bt_activity_hook).setOnClickListener(this);
        findViewById(R.id.bt_context_hook).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.bt_say_hello:
                mTextViewHello.setText("Hello " + mEditTextName.getText().toString() + "!");
                break;
            case R.id.bt_static_proxy:
                break;
            case R.id.bt_dynamic_proxy:
                break;
            case R.id.bt_activity_hook:
                onActivityHook();
                break;
            case R.id.bt_context_hook:
                onContextHook();
                break;
            default:
                mTextViewInfo.setText("Unknown Click");
                break;
        }
    }

    private void onContextHook(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("https://www.baidu.com"));

        // 注意这里使用的ApplicationContext 启动的Activity
        // 因为Activity对象的startActivity使用的并不是ContextImpl的mInstrumentation
        // 而是自己的mInstrumentation, 如果你需要这样, 可以自己Hook
        // 比较简单, 直接替换这个Activity的此字段即可.
        getApplicationContext().startActivity(intent);
    }

    private void onActivityHook(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("https://www.baidu.com"));

        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        try {
            HookHelper.attachContext();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
