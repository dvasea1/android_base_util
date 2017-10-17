package com.ebs.androidutilbase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebs.android_base_utility.base.recyclerview_utils.BaseAdapterRecycler;
import com.ebs.android_base_utility.base.recyclerview_utils.ExStaggeredGridLayoutManager;

import java.util.List;

/**
 * Created by barbaros.vasile on 10/17/2017.
 */

public class AdapterTest extends BaseAdapterRecycler {

    private List<Item> list;
    private Context context;

    public AdapterTest(final List<Item> list){
        super(list);
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        this.context = context;
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}