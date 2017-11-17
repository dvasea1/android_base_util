package com.ebs.android_base_utility.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ebs.android_base_utility.R;
import com.ebs.android_base_utility.base.util.LoadingView;
import com.ebs.android_base_utility.base.util.LocalBroadCastReceiver;
import com.ebs.android_base_utility.base.util.NavigationBar;
import com.ebs.android_base_utility.base.util.StatusBarUtil;
import com.google.gson.Gson;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment implements BaseInterface {

    protected View view;
    protected View loadingView;
    protected LayoutInflater inflater;
    private boolean isActivityCreated = false;
    private boolean isFragmentVisible = false;
    private BroadcastReceiver receiver;
    View topBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeKeyboard();
        onCreated();
    }

    @Override
    public Animation onCreateAnimation (int transit, boolean enter, int nextAnim) {
        Animation anim = super.onCreateAnimation(transit, enter, nextAnim);
        if (anim == null && nextAnim != 0) {
            anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);
        }
        if (anim != null) {
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    System.out.println("XXX anim exists "+getClass().getCanonicalName()+" isActivityCreated "+ isActivityCreated+" isFragmentVisible "+isFragmentVisible);
                    if(isActivityCreated && isFragmentVisible){
                        onActivityCreated();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        } else {

            System.out.println("XXX anim null "+getClass().getCanonicalName()+" isActivityCreated "+ isActivityCreated+" isFragmentVisible "+isFragmentVisible);
            if(isActivityCreated && isFragmentVisible) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onActivityCreated();
                    }
                }, 500);
            }
        }
        return anim;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        view = inflater.inflate(getLayoutResourceId(), container, false);
        ButterKnife.bind(this, view);
        createLoadingView(getRootLoadingViewResId());
        onViewCreated();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isFragmentVisible = isVisibleToUser;
        System.out.println("XXX setUserVisibleHint "+getClass().getCanonicalName()+" isActivityCreated "+ isActivityCreated+" isFragmentVisible "+isFragmentVisible);
        if(isActivityCreated && isFragmentVisible) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    onActivityCreated();
                }
            }, 500);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isActivityCreated = true;
        isFragmentVisible = getUserVisibleHint();
        getNavigation((ViewGroup) view);
        if(topBar != null){
            if(topBar instanceof NavigationBar){
                StatusBarUtil.addStatus(getActivity(),(LinearLayout) topBar);
            }
        }
    }

    private void getNavigation(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                getNavigation((ViewGroup) child);
                if (child instanceof NavigationBar) {
                    topBar = child;
                    break;
                }
            }
        }
    }
    private void createLoadingView(int resId){
        RelativeLayout rootView = view.findViewById(resId);
        if(rootView == null){
            loadingView = new LoadingView().getProgressBar(getActivity(),view,getLayoutResourceIdLoading());
        } else {
            loadingView = new LoadingView().getProgressBar(getActivity(),rootView,getLayoutResourceIdLoading());
        }
    }

    @Override
    public int getLayoutResourceId() {
        return 0;
    }

    @Override
    public int getRootLoadingViewResId() {
        return 0;
    }

    @Override
    public int getLayoutResourceIdLoading() {
        return 0;
    }

    @Override
    public void onCreated() {

    }

    @Override
    public void onViewCreated() {

    }

    @Override
    public void onActivityCreated() {
        isActivityCreated = false;
        System.out.println("XXX onActivityCreated "+getClass().getCanonicalName());
    }

    public void registerBroadCastReceiver(final LocalBroadCastReceiver broadcastReceiver){
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try{
                    if(getActivity() != null && isAdded() && intent.getExtras() != null){
                        broadcastReceiver.Receive(context,intent.getExtras().getString("action"),intent.getExtras().getString("sender"),intent.getExtras().getString("data"));
                    }
                } catch (Exception e){e.printStackTrace();}
            }
        };
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,new IntentFilter("filter"));
    }

    public void sendBroadCast(String action,Object o,String sender){
        Intent intent = new Intent("filter");
        intent.putExtra("action",action);
        intent.putExtra("data",new Gson().toJson(o));
        intent.putExtra("sender",sender);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    protected void changeFragment(int idContainer, Fragment fragment, boolean addToBackStack,boolean animate,boolean replace){
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(animate) {
                fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_left_enter,
                        R.anim.fragment_slide_left_exit,
                        R.anim.fragment_slide_right_enter,
                        R.anim.fragment_slide_right_exit);
            }
            if(replace) {
                fragmentTransaction.replace(idContainer, fragment);
            } else {
                fragmentTransaction.add(idContainer, fragment);
            }
            if(addToBackStack) {
                fragmentTransaction.addToBackStack(fragment.getClass().getName());
            }
            fragmentTransaction.commit();
        } catch (Exception e){e.printStackTrace();}
    }

    public void pop(){
        if(getActivity() != null && isAdded()){
            getActivity().onBackPressed();
        }
    }

    public void hideKeyboard(View view) {
        if(view != null) {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void removeKeyboard(){
        try {
            (getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            if (((getActivity()).getCurrentFocus() != null) && ((getActivity()).getCurrentFocus().getWindowToken() != null)) {
                InputMethodManager inputMethodManager = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
                if(inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow((getActivity()).getCurrentFocus().getWindowToken(), 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeKeyboard();
        if(receiver!=null){
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
        }
    }
}