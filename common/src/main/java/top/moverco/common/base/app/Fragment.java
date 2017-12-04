package top.moverco.common.common.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.moverco.common.common.widget.convention.PlaceHolderView;

/**
 * @author Jamal
 */

public abstract class Fragment extends android.support.v4.app.Fragment {
    protected View mRoot;
    protected Unbinder mUnbinder;
    protected PlaceHolderView mPlaceHolderView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //初始化参数
        initArgs(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRoot == null) {
            int id = getContentLayoutId();
            View root = inflater.inflate(id, container, false);
            initWidget(root);
            mRoot = root;
        } else {
            if (mRoot.getParent() != null) {
                ((ViewGroup) mRoot.getParent()).removeView(mRoot);
            }
        }
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //当view初始化完成之后创建数据
        initData();
    }

    protected abstract int getContentLayoutId();

    protected void initWidget(View root) {
        mUnbinder = ButterKnife.bind(this, root);
    }

    protected void initData() {
    }

    protected void initArgs(Bundle bundle) {
    }

    /**
     * 返回按键出发时调用
     *
     * @return 返回true表示已处理返回逻辑，Activity不用自己finish
     * 返回false表示没有处理返回逻辑。
     */
    public boolean onBackPressed() {
        return false;
    }

    /**
     * 设置占位布局
     * @param placeHolderView
     */
    public void setPlaceHolderView(PlaceHolderView placeHolderView){
        this.mPlaceHolderView = placeHolderView;
    }
}
