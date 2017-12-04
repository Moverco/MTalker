package top.moverco.common.common.widget.recycler;

/**
 * @author Jamal
 */

public interface AdapterCallback<Data> {
    void update(Data data,RecyclerAdapter.ViewHolder<Data> holder);

}
