package com.ebs.androidutilbase;

import com.ebs.android_base_utility.base.BaseFragmentActivity;
import com.ebs.android_base_utility.base.recyclerview_utils.RecyclerLazyLoad;

import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

RecyclerLazyLoad recyclerLazyLoad;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreated() {
        super.onActivityCreated();
      ///change();
        recyclerLazyLoad = new RecyclerLazyLoad(null,null);
        recyclerLazyLoad.setNext(false);
        System.out.println("NExt is "+recyclerLazyLoad.getNext());
    }

    @OnClick(R.id.btn) void click(){
        changeFragmentPopup(R.id.root,MyFragment.newInstance(),true,true,false);
    }
    public void change(){
        changeFragmentPopup(R.id.root,MyFragment.newInstance(),true,true,false);
    }
}
