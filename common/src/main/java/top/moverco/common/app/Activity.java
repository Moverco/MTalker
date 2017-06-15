package top.moverco.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by liuzongxiang on 15/06/2017.
 */

public abstract class Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindows();
        if (initArgs(getIntent().getExtras())) {
            int layoutId = getContentLayoutId();
            setContentView(layoutId);
            initWidget();
            initData();
        } else {
            finish();
        }
    }

    protected void initWindows() {
    }

    /*
     *获取当前界面的资源ID
     */
    protected abstract int getContentLayoutId();

    protected boolean initArgs(Bundle bundle) {
        return true;
    }

    //初始化控件
    protected void initWidget() {
    }

    //初始化数据
    protected void initData() {
    }

    @SuppressWarnings("RestrictedApi")
    @Override
    public void onBackPressed() {
        List<android.support.v4.app.Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment:fragments){
            if (fragment instanceof top.moverco.common.app.Fragment){
                if (((top.moverco.common.app.Fragment) fragment).onBackPressed())
                    return;
            }
        }
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
