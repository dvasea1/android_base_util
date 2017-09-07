package com.ebs.android_base_utility.base.recyclerview_utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.ebs.android_base_utility.R;


public class LoadingFooter extends RelativeLayout {

    protected State mState = State.Normal;
    private View mLoadingView;

    public LoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        inflate(context, R.layout.loading_footer, this);
        setOnClickListener(null);

        setState(State.Normal, true);
    }

    public State getState() {
        return mState;
    }

    public void setState(State status ) {
        setState(status, true);
    }

    public void setState(State status, boolean showView) {
        if (mState == status) {
            return;
        }
        mState = status;

        switch (status) {

            case Normal:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(INVISIBLE);
                } else {
                    mLoadingView = findViewById(R.id.progress);
                    mLoadingView.setVisibility(INVISIBLE);
                }
                findViewById(R.id.waitLayout).setVisibility(VISIBLE);
                break;
            case Loading:
                setOnClickListener(null);
                if (mLoadingView != null) {
                    mLoadingView.setVisibility(VISIBLE);
                } else {
                    mLoadingView = findViewById(R.id.progress);
                    mLoadingView.setVisibility(VISIBLE);
                }
                findViewById(R.id.waitLayout).setVisibility(VISIBLE);
                break;
            case TheEnd:
                setOnClickListener(null);
               findViewById(R.id.waitLayout).setVisibility(GONE);
                break;
            default:

                break;
        }
    }

    public static enum State {
        Normal, TheEnd, Loading
    }
}