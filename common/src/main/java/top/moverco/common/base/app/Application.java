package top.moverco.common.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.widget.Toast;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamal
 */

public class Application extends android.app.Application {
    private static Application instance;
    private List<android.app.Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(android.app.Activity activity, Bundle savedInstanceState) {
                mActivities.add(activity);
            }

            @Override
            public void onActivityStarted(android.app.Activity activity) {

            }

            @Override
            public void onActivityResumed(android.app.Activity activity) {

            }

            @Override
            public void onActivityPaused(android.app.Activity activity) {

            }

            @Override
            public void onActivityStopped(android.app.Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(android.app.Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(android.app.Activity activity) {
                mActivities.remove(activity);
            }
        });
    }
    public static Application getInstance(){
        return instance;
    }

    public void finishAll(){
        for (android.app.Activity activity:mActivities){
            activity.finish();
        }
        showAccountView(this);
    }
    protected void showAccountView(Context context){

    }
    public static void showToast(final String msg){
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                Toast.makeText(instance,msg,Toast.LENGTH_LONG).show();
            }
        });
    }
    public static void showToast(@StringRes int strRes){
        showToast(instance.getString(strRes));
    }
}
