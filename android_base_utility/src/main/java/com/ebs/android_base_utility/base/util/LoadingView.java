package com.ebs.android_base_utility.base.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ebs.android_base_utility.R;


public class LoadingView {

    public View getProgressBar(Activity context,View view)
    {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.loading_view, null);
        v.setVisibility(View.INVISIBLE);
        if(view!=null){
            ViewGroup layout = (ViewGroup) view;
            layout.addView(v);
        } else {
            ViewGroup layout = (ViewGroup) context.getWindow().getDecorView();
            layout.addView(v);
        }

        return v;
    }
}
