package com.ebs.android_base_utility.base.recyclerview_utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Vasile-barbaros-Pc on 8/26/2016.
 */
public abstract class BaseAdapterRecycler <T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mObjects;
    private ItemClickListener itemClickListener;
    private View emptyView;
    private boolean DEBUG = false;

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    public BaseAdapterRecycler(final List<T> objects) {
        mObjects = objects;
registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
    @Override
    public void onChanged() {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        checkIfEmpty();
    }
});
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(final T object) {
        mObjects.add(object);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        final int size = getItemCount();
        mObjects.clear();
        notifyItemRangeRemoved(0, size);
        if(dataEmptyObserver!=null)dataEmptyObserver.onDataChanged(false);
    }

    /*private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            checkIfEmpty();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            checkIfEmpty();
        }
    };*/

    /*@Override
    public int getItemCount() {
        if(mObjects.size()>0){
            if(dataEmptyObserver!=null)dataEmptyObserver.onDataChanged(false);
        } else {
            if(dataEmptyObserver!=null)dataEmptyObserver.onDataChanged(true);
        }
        return mObjects.size();
    }*/

    private void checkIfEmpty(){
        /*if(DEBUG){
            System.out.println("dataEmptyObserver isEmpty "+isEmpty+" emptyView "+emptyView);
        }*/
        if (emptyView != null) {
            final boolean emptyViewVisible =
                    getItemCount() == 0;
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            //setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
        }
    }

    DataEmptyObserver dataEmptyObserver = new DataEmptyObserver() {
        @Override
        public void onDataChanged(boolean isEmpty) {
            if(DEBUG){
                System.out.println("dataEmptyObserver isEmpty "+isEmpty+" emptyView "+emptyView);
            }
            if(isEmpty){
                if(emptyView!=null)emptyView.setVisibility(View.VISIBLE);
            } else {
                if(emptyView!=null)emptyView.setVisibility(View.GONE);
            }
        }
    };

    public T getItem(final int position) {
        return mObjects.get(position);
    }

    public long getItemId(final int position) {
        return position;
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(final T item) {
        return mObjects.indexOf(item);
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(final T object, int index) {
        mObjects.add(index, object);
        notifyItemInserted(index);

    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(T object) {
        final int position = getPosition(object);
        mObjects.remove(object);
        notifyItemRemoved(position);
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained in this adapter.
     */
    public void sort(Comparator<? super T> comparator) {
        Collections.sort(mObjects, comparator);
        notifyItemRangeChanged(0, getItemCount());
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void setEmptyView(View emptyView){
        this.emptyView=emptyView;
    }

    public void onItemClick(View view , int position){
        if(itemClickListener!=null){
            itemClickListener.onItemClick(view,position);
        }
    }

    public interface ItemClickListener{
        public void onItemClick(View view, int position);
    }

    public interface DataEmptyObserver{
        public void onDataChanged(boolean isEmpty);
    }

    public void notifyItem(Object item){
        int index = 0;
        for (int i = 0; i < mObjects.size(); i++) {
            if(mObjects.get(i).equals(item)){
                index = i;
                break;
            }
        }
        System.out.println("notifyItemChanged "+index);
        notifyItemChanged(index);
    }
    public void notifyItemInserted(Object item){
        int index = 0;
        for (int i = 0; i < mObjects.size(); i++) {
            if(mObjects.get(i).equals(item)){
                index = i;
                break;
            }
        }
        notifyItemInserted(index);
    }
}