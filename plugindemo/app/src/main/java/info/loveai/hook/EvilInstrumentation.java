package info.loveai.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Method;

public class EvilInstrumentation extends Instrumentation {
    private  static final String TAG = EvilInstrumentation.class.getSimpleName();
    private Instrumentation mBase;
    public EvilInstrumentation(Instrumentation base){
        mBase = base;
    }

    public ActivityResult execStartActivity(
            Context who,
            IBinder contextThread,
            IBinder token,
            Activity target,
            Intent intent,
            int requestCode,
            Bundle options
    ){
        Log.d(TAG, "\nexecStartActivity, parameters: "
                + "\nwho = [" + who + "], "
                + "\ncontextThread = [" + contextThread + "], "
                + "\ntoken = [" + token + "], "
                +"\ntarget = [" + target + "], "
                + "\nintent = [" + intent + "],"
                + "\nrequestCode = [" + requestCode + "],"
                + "\noptions = [" + options + "]"
        );

        try {
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity",
                    Context.class,
                    IBinder.class,
                    IBinder.class,
                    Activity.class,
                    Intent.class,
                    int.class,
                    Bundle.class);

            execStartActivity.setAccessible(true);

            return (ActivityResult) execStartActivity.invoke(
                    mBase,
                    who,
                    contextThread,
                    token,
                    target,
                    intent,
                    requestCode,
                    options);
        } catch (Exception e) {
            // Custom ROM
            throw new RuntimeException("Not support ROM!");
        }
    }
}
