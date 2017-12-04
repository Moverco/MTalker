package top.moverco.mtalker;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import top.moverco.common.base.app.Activity;
import top.moverco.common.util.LogUtil;
import top.moverco.common.base.widget.recycler.RecyclerAdapter;

/**
 * @author Jamal
 */
public class MainActivity extends Activity {
    List<String> mList = new ArrayList<>();
    private static final int CONTENT_LAYOUT_ID = R.layout.activity_main;

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;


    @Override
    protected void initData() {
        super.initData();
        for (int i = 0; i < 10; i++) {
            mList.add(i + "");
        }
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerAdapter.AdapterListener adapterListener = new RecyclerAdapter.AdapterListener() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Object o) {
                LogUtil.shortToast("item click");
            }

            @Override
            public void onItemLongClick(RecyclerAdapter.ViewHolder holder, Object o) {
                LogUtil.shortToast("item long click");
            }
        };
        RecyclerAdapter recyclerAdapter = new RecyclerViewAdapter(mList,adapterListener);
        mRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    protected int getContentLayoutId() {
        return CONTENT_LAYOUT_ID;
    }


}
