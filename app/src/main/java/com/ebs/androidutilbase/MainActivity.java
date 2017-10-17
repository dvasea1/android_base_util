package com.ebs.androidutilbase;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ebs.android_base_utility.base.BaseFragmentActivity;
import com.ebs.android_base_utility.base.recyclerview_utils.RecyclerLazyLoad;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

RecyclerLazyLoad recyclerLazyLoad;
    private List<Item >items = new ArrayList<>();

    @BindView(R.id.recycler)
    RecyclerView recyclerViewl;

    @BindView(R.id.empty)
    View empty;

    @Override
    public int getLayoutResourceIdLoading() {
        return R.layout.loading;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreated() {
        super.onActivityCreated();
      ///change();

        AdapterTest adapterTest = new AdapterTest(items);
        recyclerLazyLoad = new RecyclerLazyLoad(thisActivity,recyclerViewl,adapterTest);
        recyclerLazyLoad.setAdapter(getDialogContext());
        recyclerLazyLoad.setEmptyView(empty);
        recyclerLazyLoad.setResourceLoading(R.layout.loadingfoots,R.id.root,R.id.progress);
        loadingView.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();

                setItems();
            }
        }, 5000);   //5 seconds
    }



    void setItems() {
        loadingView.setVisibility(View.GONE);
        List<Item> temp = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item();
            temp.add(item);
        }

        recyclerLazyLoad.addItems(items,temp);
    }

    @OnClick(R.id.button)void click(){
        recyclerLazyLoad.reset();
        loadingView.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                loadingView.setVisibility(View.GONE);
                recyclerLazyLoad.addItems(items,new ArrayList<>());
                //setItems();
            }
        }, 5000);   //5 seconds
    }



}
