package info.loveai.hook;

import android.app.Instrumentation;

public class EvilInstrumentation extends Instrumentation {
    private Instrumentation mBase;
    public EvilInstrumentation(Instrumentation base){
        mBase = base;
    }
}
