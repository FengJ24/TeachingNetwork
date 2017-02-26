package com.university.education.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.university.education.R;


/**
 * Created by Administrator on 2016/12/13.
 */

public class RefreshLoadMoreListView extends ListView implements AbsListView.OnScrollListener {
    private int mScreenWidth;    // 屏幕宽度
    private int mDownX;            // 按下点的x值
    private int mDownY;            // 按下点的y值
    private int mDeleteBtnWidth;// 删除按钮的宽度

    private boolean isDeleteShown;    // 删除按钮是否正在显示

    private ViewGroup mPointChild;    // 当前处理的item
    private LinearLayout.LayoutParams mLayoutParams;    // 当前处理的item的LayoutP
    private LinearLayout.LayoutParams lastLayoutParams;    // 当前处理的item的LayoutP
    private int pointToPosition;
    private LinearLayout linearlayout;
    private static final int DONE = 0;
    private static final int PULL_TO_REFRESH = 1;
    private static final int RELEASE_TO_REFRESH = 2;
    private static final int REFRESHING = 3;
    private static final float RATIO = 3;// 用来设置实际间距和上边距之间的比例

    private int state;// 当前下拉刷新的状态

    private int firstVisibleIndex;// 在listview中第一个可以看见的item
    private View headView;
    private ImageView headArrow;
    private ProgressBar progressBar;
    private TextView headTitle;
//    private TextView headLastUpdate;
    private int headContentWidth;
    private int headContentHeight;
    private Animation animation;
    private Animation reverseAnimation;
    private OnRefreshListner refreshListner;// 刷新监听器

    private boolean isRefreshable;
    private boolean isRecored = false;// 用来记录第一次按下坐标点,在整个滑动的过程中 只记录一次
    private float startY;
    private boolean isBack = false;// 是从 松开刷新状态 来到的 下拉刷新状态
    private boolean isShown;
    private boolean isItemClick = true;
    private boolean isAlreadyshown;
    private View mFootView;
    private int mFootViewMeasuredHeight;
    private boolean backState;

    public RefreshLoadMoreListView(Context context) {
        this(context, null);
    }

    public RefreshLoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshLoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initFootView();
    }


    private void init(Context context) {
        // listview 设置滑动时缓冲背景色
        setCacheColorHint(0x00000000);

        headView = View.inflate(context, R.layout.head, null);

        headArrow = (ImageView) headView.findViewById(R.id.head_arrow);
        progressBar = (ProgressBar) headView.findViewById(R.id.progressbar);
        headTitle = (TextView) headView.findViewById(R.id.head_title);
//        headLastUpdate = (TextView) headView
//                .findViewById(R.id.head_last_update);

        headArrow.setMinimumWidth(50);
        headArrow.setMinimumHeight(70);

        MeasureView(headView);

        headContentWidth = headView.getMeasuredWidth();
        headContentHeight = headView.getMeasuredHeight();

        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        // 为listView加入顶部View
        addHeaderView(headView);

        setOnScrollListener(this);

        animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(250);
        animation.setFillAfter(true);// 设定动画结束时，停留在动画结束位置 (保留动画效果)
        animation.setInterpolator(new LinearInterpolator());// 匀速变化

        reverseAnimation = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);// 设定动画结束时，停留在动画结束位置 (保留动画效果)
        reverseAnimation.setInterpolator(new LinearInterpolator());// 匀速变化

        // 设置当前headView的状态
        state = DONE;

        // 设置当前下拉刷新是否可用
        isRefreshable = false;
    }


    /**
     * 测量headView的 宽高
     *
     * @param child
     */
    private void MeasureView(View child) {
        ViewGroup.LayoutParams lp = child.getLayoutParams();
        if (null == lp) {
            lp = new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT);
        }
        int measureChildWidth = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int measureChildHeight;
        int height = lp.height;

        if (lp.height > 0) {
            measureChildHeight = MeasureSpec.makeMeasureSpec(lp.height,
                    MeasureSpec.EXACTLY);
        } else {
            measureChildHeight = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(measureChildWidth, measureChildHeight);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                firstVisibleIndex = getFirstVisiblePosition();
                int lastVisiblePosition = getLastVisiblePosition();
                if (lastVisiblePosition ==getAdapter().getCount()-1){
                    requestDisallowInterceptTouchEvent(true);
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    requestDisallowInterceptTouchEvent(false);
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (firstVisibleIndex == 0 && !isRecored) {

                    startY = ev.getY();
                    isRecored = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                firstVisibleIndex = getFirstVisiblePosition();
                int lastVisible = getLastVisiblePosition();
                if (lastVisible ==getAdapter().getCount()-1){
                    requestDisallowInterceptTouchEvent(true);
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else{
                    requestDisallowInterceptTouchEvent(false);
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                float tempY = ev.getY();
                if (firstVisibleIndex == 0 && !isRecored) {
                    startY = tempY;
                    isRecored = true;
                }

                if (state != REFRESHING && firstVisibleIndex == 0) {
                    if (state == PULL_TO_REFRESH) {
                        // 向下拉了 从下拉刷新的状态 来到 松开刷新的状态
                        if ((tempY - startY) / RATIO >= headContentHeight
                                && (tempY - startY) > 0) {
                            state = RELEASE_TO_REFRESH;

                            changeHeadViewOfState();
                        }
                        // 向上推了 从下拉刷新的状态 来到 刷新完成的状态
                        else if ((tempY - startY) <= 0) {
                            state = DONE;

                            changeHeadViewOfState();
                        }

                    } else if (state == RELEASE_TO_REFRESH) {
                        // 向上推了 还没有完全将HEADVIEW 隐藏掉（可以看到一部分）
                        // 从松开刷新的状态 来到 下拉刷新的状态
                        if ((tempY - startY) / RATIO < headContentHeight
                                && (tempY - startY) > 0) {
                            state = PULL_TO_REFRESH;

                            changeHeadViewOfState();
                            isBack = true;
                        }
                        // 向上推了 一下子推到了最上面 从松开刷新的状态 来到 刷新完成的状态 （数据不刷新的）
                        else if ((tempY - startY) <= 0) {
                            state = DONE;
                            changeHeadViewOfState();
                        }
                    } else if (state == DONE) {
                        // 刷新完成的状态 来到 下拉刷新的状态
                        if ((tempY - startY) > 0) {
                            state = PULL_TO_REFRESH;
                            changeHeadViewOfState();
                        }
                    }
                    if (state == PULL_TO_REFRESH) {
                        headView.setPadding(
                                0,
                                (int) ((tempY - startY) / RATIO - headContentHeight),
                                0, 0);
                    }
                    if (state == RELEASE_TO_REFRESH) {
                        headView.setPadding(
                                0,
                                (int) ((tempY - startY) / RATIO - headContentHeight),
                                0, 0);
                    }

                }
                return super.onTouchEvent(ev);
            case MotionEvent.ACTION_UP:
                if (state != REFRESHING) {
                    if (state == PULL_TO_REFRESH) {
                        // 松手
                        state = DONE;

                        changeHeadViewOfState();
                    } else if (state == RELEASE_TO_REFRESH) {
                        // 松手
                        state = REFRESHING;
                        changeHeadViewOfState();
                        // 执行数据刷新方法
                        onRefresh();
                    }
                }
                isRecored = false;
                isBack = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 执行下拉刷新
     */
    private void onRefresh() {
        if (refreshListner != null) {
            refreshListner.onRefresh();
        }
    }

    /**
     * HeadView的状态变化效果
     */
    private void changeHeadViewOfState() {
        //
        switch (state) {

            case PULL_TO_REFRESH:
                headArrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.VISIBLE);
//                headLastUpdate.setVisibility(View.VISIBLE);
                headArrow.clearAnimation();
                headTitle.setText("下拉刷新");
                //由 松开刷新  到  下拉刷新
                if (isBack) {

                    headArrow.startAnimation(animation);
                    isBack = false;
                }
                break;
            case RELEASE_TO_REFRESH:
                headArrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.VISIBLE);
//                headLastUpdate.setVisibility(View.VISIBLE);
                headArrow.clearAnimation();
                headArrow.startAnimation(reverseAnimation);
                headTitle.setText("松开刷新");
                break;

            case REFRESHING:
                headArrow.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                headTitle.setVisibility(View.VISIBLE);
//                headLastUpdate.setVisibility(View.VISIBLE);
                headArrow.clearAnimation();
                headTitle.setText("正在刷新");
                headView.setPadding(0, 0, 0, 0);
                break;
            case DONE:
                headArrow.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                headTitle.setVisibility(View.VISIBLE);
//                headLastUpdate.setVisibility(View.VISIBLE);

                headArrow.clearAnimation();
                headTitle.setText("下拉可以刷新");

                headView.setPadding(0, -1 * headContentHeight, 0, 0);

                break;
        }

    }

    public boolean setBackState(boolean state) {
        this.backState = state;
        return state;
    }

    private int lastPos;//最后一个可见的item的位置
    private int count;//item总数,注意不是当前可见的item总数


    /**
     * 设置下拉刷新监听
     *
     * @param listener
     */
    public void setOnRefreshListner(OnRefreshListner listener) {
        // 设置下拉刷新可用
        isRefreshable = true;
        refreshListner = listener;

    }


    /**
     * 下拉刷新监听器
     *
     * @author lxj
     */
    public interface OnRefreshListner {
        /**
         * 下拉刷新的时候,在这里执行获取数据的过程
         */
        void onRefresh();
    }

    /**
     * 上拉刷新监听器
     *
     * @author lxj
     */
    public interface OnFootLoadingListener {
        /**
         * 这里是执行后台获取数据的过程
         */
        void onFootLoading();
    }


    /**
     * 上拉刷新完成时 所执行的操作,更改状态,隐藏head
     */
    public void onRefreshComplete() {
        state = DONE;
        changeHeadViewOfState();

//        headLastUpdate.setText("最后刷新时间： " + new Date().toLocaleString());
    }

    @Override
    public void setAdapter(ListAdapter adapter) {

//        headLastUpdate.setText("最后刷新时间： " + new Date().toLocaleString());

        super.setAdapter(adapter);
    }

    private void initFootView() {
        mFootView = View.inflate(getContext(), R.layout.footer, null);

        this.addFooterView(mFootView);
        mFootView.measure(0, 0);
        mFootViewMeasuredHeight = mFootView.getMeasuredHeight();
        //将脚布局隐藏
        mFootView.setPadding(0, 0, 0, -mFootViewMeasuredHeight);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //如果是惯性滑动或者滑动停止那就进行判断
        if (scrollState == OnScrollListener.SCROLL_STATE_FLING || scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
            //判断是否滑到最后一个item
            if (RefreshLoadMoreListView.this.getAdapter().getCount() == RefreshLoadMoreListView.this.getLastVisiblePosition() + 1) {
                if (RefreshLoadMoreListView.this.getAdapter().getCount() >= 10) {
                    if (onLoadingMoreListener != null) {
                        //将脚布局显示
                        mFootView.setPadding(0, 0, 0, 0);
                        //这里至于为什么将 loadingMore写在脚布局显示的下面,是因为这个接口回调,在实现了之后,只有走完实现
                        //类中的所有方法才会继续往下走
                        onLoadingMoreListener.loadingMore();
                    }
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 隐藏脚,默认隐藏脚后显示的内容是 已加载全部
     */
    public void hideFootView() {
        mFootView.setPadding(0, 0, 0, -mFootViewMeasuredHeight);
    }

    /**
     * 加载完数据之后显示的内容是name
     */
//    public void dataLoadingFinished(String name) {
//        mProgressBar.setVisibility(View.GONE);
//        mFootView.setPadding(0,0,0,0);
//        mTextView.setText(name);
//        mFootView.setPadding(0, 0, 0, 0);
//    }

    /**
     * 回调接口--加载更多
     */
    public interface OnLoadingMoreListener {
        //加载更多
        void loadingMore();
    }

    public void setOnLoadingMoreListener(OnLoadingMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }

    private OnLoadingMoreListener onLoadingMoreListener;

}

