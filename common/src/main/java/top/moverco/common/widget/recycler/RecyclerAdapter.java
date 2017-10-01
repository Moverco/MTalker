package top.moverco.common.widget.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import top.moverco.common.R;

/**
 * @author  Moverco
 */

public abstract class RecyclerAdapter<Data>
        extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {
    private List<Data> mDataList = new ArrayList<>();
    private AdapterListener<Data> mListener;

    public RecyclerAdapter(List<Data> dataList) {
        mDataList = dataList;
    }

    public RecyclerAdapter(AdapterListener<Data> listener) {
        mListener = listener;
    }

    public RecyclerAdapter(List<Data> datas, AdapterListener<Data> listener) {
        mDataList = datas;
        mListener = listener;
    }

    /**
     * 创建一个Viewholder
     *
     * @param parent   RecyclerView
     * @param viewType 界面类型，约定为XML布局的Id
     * @return ViewHolder
     */
    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> holder = onCreateViewHolder(root, viewType);
        root.setTag(R.id.tag_recycler_holder, holder);

        root.setOnClickListener(this);
        root.setOnLongClickListener(this);

        holder.mUnbinder = ButterKnife.bind(holder, root);
        holder.mCallback = this;
        return holder;
    }

    /**
     * @param root     根布局
     * @param viewType XML的Id
     * @return
     */

    protected abstract ViewHolder<Data> onCreateViewHolder(View root, int viewType);

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    /**
     * @param position
     * @param data
     * @return XML文件的Id用于创建ViewHolder
     */
    protected abstract int getItemViewType(int position, Data data);

    /**
     * 绑定数据到viewholder
     *
     * @param holder   ViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //获取需要绑定的数据
        Data data = mDataList.get(position);
        //触发Holder的绑定方法
        holder.bind(data);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);
    }

    /**
     * 插入一堆数据，并通知这段集合更新
     *
     * @param datas Data
     */
    public void add(Data... datas) {
        if (datas != null && datas.length > 0) {
            int startPosition = mDataList.size();
            Collections.addAll(mDataList, datas);
            notifyItemRangeChanged(startPosition, datas.length);
        }
    }

    public void add(Collection<Data> datas) {
        if (datas != null && datas.size() > 0) {
            int startPosition = mDataList.size();
            mDataList.addAll(datas);
            notifyItemRangeChanged(startPosition, datas.size());
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    public void replace(Collection<Data> datas) {
        mDataList.clear();
        if (datas != null && datas.size() > 0) {
            mDataList.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            this.mListener.onItemClick((ViewHolder) viewHolder, mDataList.get(pos));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag(R.id.tag_recycler_holder);
        if (this.mListener != null) {
            int pos = viewHolder.getAdapterPosition();
            mListener.onItemLongClick((ViewHolder) viewHolder, mDataList.get(pos));
        }
        return false;
    }

    public void setListener(AdapterListener<Data> adapterListener) {
        this.mListener = adapterListener;
    }

    /**
     * 自定义监听器
     *
     * @param <Data>
     */
    public interface AdapterListener<Data> {
        //当点击时触发
        void onItemClick(RecyclerAdapter.ViewHolder holder, Data data);

        //当长按时触发
        void onItemLongClick(RecyclerAdapter.ViewHolder holder, Data data);
    }

    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {

        private Unbinder mUnbinder;
        protected Data mData;
        private AdapterCallback<Data> mCallback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 用于绑定数据的触发
         *
         * @param data
         */
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        protected abstract void onBind(Data data);

        public void updateData(Data data) {
            if (this.mCallback!=null){
                this.mCallback.update(data,this);
            }
        }
    }
}
