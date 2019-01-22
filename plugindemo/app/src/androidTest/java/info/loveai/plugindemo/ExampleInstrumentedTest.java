package info.loveai.plugindemo;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private static final String LOG_TAG = "test";
    @Test
    public void useAppContext() {
        Log.d(LOG_TAG,"useAppContext start...");
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("info.loveai.plugindemo", appContext.getPackageName());

        Log.d(LOG_TAG,"useAppContext end .");
    }

    @Test
    public void testNativeLibraries(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        String nativeLibraryDir = appContext.getApplicationInfo().nativeLibraryDir;
        Log.d(LOG_TAG,nativeLibraryDir);
    }
}
