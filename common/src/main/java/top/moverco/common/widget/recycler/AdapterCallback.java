package top.moverco.common.widget.recycler;

/**
 * Created by liuzongxiang on 15/06/2017.
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);

}
