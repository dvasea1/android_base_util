package com.ebs.android_base_utility.base.recyclerview_utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbaros.vasile on 12/12/2016.
 */

public class RecyclerLazyLoad {
    public static int FEEDLIMIT = 20;
    private int offset = 0;
    private int limit = FEEDLIMIT;
    private RecyclerView recyclerView;
    private List<Object> tempObjects = new ArrayList<>();
    private List<Object> objects = new ArrayList<>();
    private LoadResponseListener loadInterface;
    private boolean isMoreDataAvailable = true;
    private FragmentActivity activity;
    private RecyclerView.Adapter adapter;
    private HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View tToolbar;
    private View emptyView;
    private @LayoutRes  int resourceLayout;
    private  @IdRes  int ResourceIdRoot;
    private @IdRes  int ResourceIdProgress;
    private Boolean next;

    public RecyclerLazyLoad(FragmentActivity activity, RecyclerView recyclerView, RecyclerView.Adapter adapter){
        this.recyclerView = recyclerView;
        this.activity = activity;
        this.adapter = adapter;
    }
    public RecyclerLazyLoad(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }

    public void setLoadInterface(LoadResponseListener loadInterface) {
        this.loadInterface = loadInterface;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        setRefresh();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setResourceLoading(@LayoutRes final int resourceLayout, @IdRes final int ResourceIdRoot, @IdRes final int ResourceIdProgress){
        this.resourceLayout = resourceLayout;
        this.ResourceIdRoot = ResourceIdRoot;
        this.ResourceIdProgress = ResourceIdProgress;
    }

    public void setAdapter(RecyclerView.ItemAnimator animator, RecyclerView.LayoutManager layoutManager, RecyclerView.ItemDecoration itemDecoration){
        this.recyclerView.setItemAnimator(animator);
        this.recyclerView.setLayoutManager(layoutManager);
        if(itemDecoration!=null) {
            this.recyclerView.addItemDecoration(itemDecoration);
        }
        if(activity!=null) {
            headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(this.adapter);
            this.recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
            setScroll();
        } else {
            this.recyclerView.setAdapter(adapter);
        }
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }
    public void setAdapter(RecyclerView.ItemAnimator animator, LinearLayoutManager layoutManager, RecyclerView.ItemDecoration itemDecoration){
        this.recyclerView.setItemAnimator(animator);
        this.recyclerView.setLayoutManager(layoutManager);
        if(itemDecoration!=null) {
            this.recyclerView.addItemDecoration(itemDecoration);
        }
        if(activity!=null) {
            headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(this.adapter);
            this.recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
            setScroll();
        } else {
            this.recyclerView.setAdapter(adapter);
        }
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    public void setAdapter(Context context){
        this.recyclerView.setItemAnimator(RecyclerViewUtils.getAnimator());
        this.recyclerView.setLayoutManager(RecyclerViewUtils.getLayoutManager(context,false));
        if(activity!=null) {
            headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(this.adapter);
            this.recyclerView.setAdapter(headerAndFooterRecyclerViewAdapter);
            setScroll();
        } else {
            recyclerView.setAdapter(adapter);
        }
    }

    private void setScroll() {

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            // Keeps track of the overall vertical offset in the list
            int verticalOffset;

            // Determines the scroll UP/DOWN direction
            boolean scrollingUp;

            @Override
            public void onLoadNextPage(View view) {
                super.onLoadNextPage(view);
                System.out.println("onLoadNextPage isMoreDataAvailable "+isMoreDataAvailable);
                if(!isMoreDataAvailable){
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView,LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                }

                LoadingFooter.State state = RecyclerViewUtils.getFooterViewState(recyclerView);
                if(state == LoadingFooter.State.Loading) {
                    Log.d("@Cundong", "the state is Loading, just wait..");
                    return;
                }
                if (isMoreDataAvailable) {
                    // loading more
                    // adapter.addFooterView(new LoadingFooter(getDialogContext()));
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView,LoadingFooter.State.Loading, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                    if(loadInterface!=null)loadInterface.onLoadMore();
                    //getFolders(false,null);
                } else {
                    //the end
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView,LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                    //headerAndFooterRecyclerViewAdapter.removeFooterView(headerAndFooterRecyclerViewAdapter.getFooterView());
                }
            }

            @Override
            public void onScroll(int dx, int dy) {
                super.onScroll(dx, dy);
                if(loadInterface!=null)loadInterface.onScroll(dx,dy);
                if(tToolbar!=null) {
                    verticalOffset += dy;
                    scrollingUp = dy > 0;
                    int toolbarYOffset = (int) (dy - tToolbar.getTranslationY());
                    tToolbar.animate().cancel();
                    if (scrollingUp) {
                        if (toolbarYOffset < tToolbar.getHeight()) {
                            if (verticalOffset > tToolbar.getHeight()) {
                                //toolbarSetElevation(TOOLBAR_ELEVATION);
                            }
                            tToolbar.setTranslationY(-toolbarYOffset);
                        } else {
                            // toolbarSetElevation(0);
                            tToolbar.setTranslationY(-tToolbar.getHeight());
                        }
                    } else {
                        if (toolbarYOffset < 0) {
                            if (verticalOffset <= 0) {
                                //toolbarSetElevation(0);
                            }
                            tToolbar.setTranslationY(0);
                        } else {
                            if (verticalOffset > tToolbar.getHeight()) {
                                // toolbarSetElevation(TOOLBAR_ELEVATION);
                            }
                            tToolbar.setTranslationY(-toolbarYOffset);
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(int newState) {
                super.onScrollStateChanged(newState);
                if(loadInterface!=null)loadInterface.onScrollStateChanged(newState);
                if(tToolbar!=null) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (scrollingUp) {
                            if (verticalOffset > tToolbar.getHeight()) {
                                toolbarAnimateHide();
                            } else {
                                toolbarAnimateShow(verticalOffset);
                            }
                        } else {
                            if (tToolbar.getTranslationY() < tToolbar.getHeight() * -0.6 && verticalOffset > tToolbar.getHeight()) {
                                toolbarAnimateHide();
                            } else {
                                toolbarAnimateShow(verticalOffset);
                            }
                        }
                    }
                }
            }
        });
    }

    private void toolbarAnimateShow(final int verticalOffset) {
        tToolbar.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        //toolbarSetElevation(verticalOffset == 0 ? 0 : TOOLBAR_ELEVATION);
                    }
                });
    }

    private void toolbarAnimateHide() {
        tToolbar.animate()
                .translationY(-tToolbar.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //toolbarSetElevation(0);
                    }
                });
    }
    private void setRefresh(){
        if(swipeRefreshLayout!=null) {
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    try {
                        ((BaseAdapterRecycler) adapter).setEmptyView(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //isMoreDataAvailable = true;
                   // offset = 0;
                   // setNext(null);
                    reset();
                    loadInterface.onRefresh();
                }
            });
        }
    }

    public void addItems(List<? extends Object> objects, List<? extends Object> tempObjects){
        if(activity!=null) {
            if(emptyView != null){
               if(adapter instanceof BaseAdapterRecycler){
                   ((BaseAdapterRecycler) adapter).setEmptyView(emptyView);
               }
            }
            this.tempObjects = new ArrayList<>();
            this.tempObjects.addAll(tempObjects);
            this.objects = new ArrayList<>();
            this.objects = (List<Object>) objects;

            this.objects.addAll(tempObjects);
            adapter.notifyItemRangeInserted(adapter.getItemCount() , tempObjects.size());


            offset += tempObjects.size();

            System.out.println("onLoadNextPage added items");
            if(!isMoreDataAvailable){
                RecyclerViewUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
            }
            if(getNext() != null){
                if (!getNext()) {
                    isMoreDataAvailable = false;
                    System.out.println("no more available ");
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                } else {
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.Normal, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                }
            } else {
                if (tempObjects.size() < limit) {
                    isMoreDataAvailable = false;
                    System.out.println("no more available ");
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.TheEnd, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                } else {
                    RecyclerViewUtils.setFooterViewState(activity, recyclerView, LoadingFooter.State.Normal, resourceLayout,ResourceIdRoot,ResourceIdProgress);
                }
            }
        }
    }

    public void reset(){
        ((BaseAdapterRecycler)adapter).clear();
     /*   final int size = adapter.getItemCount();
        objects.clear();
        adapter.notifyItemRangeRemoved(0, size);*/
        if(headerAndFooterRecyclerViewAdapter.getFooterViewsCount()>0) {
            headerAndFooterRecyclerViewAdapter.removeFooterView(headerAndFooterRecyclerViewAdapter.getFooterView());
        }
        ((BaseAdapterRecycler) adapter).setEmptyView(null);
        setMoreDataAvailable(true);
        setOffset(0);
        setNext(null);
    }

    public interface LoadInterface{
        void onLoadMore();
        void onRefresh();
        void onScroll(int dx, int dy);
        void onScrollStateChanged(int newState);
    }

    public int getOffset() {
        return offset;
    }

    public int getLimit() {
        return limit;
    }

    public HeaderAndFooterRecyclerViewAdapter getHeaderAndFooterRecyclerViewAdapter() {
        return headerAndFooterRecyclerViewAdapter;
    }

    public void settToolbar(View tToolbar) {
        this.tToolbar = tToolbar;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public boolean isMoreDataAvailable() {
        return isMoreDataAvailable;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }
}
