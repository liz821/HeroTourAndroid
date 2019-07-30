package com.jaydenxiao.common.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jaydenxiao.common.baserx.RxManager;
import com.jaydenxiao.common.commonutils.TUtil;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * des:基类fragment
 * Created by xsf
 * on 2016.07.12:38
 */

/***************使用例子*********************/
//1.mvp模式
//public class SampleFragment extends BaseFragment<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleFragment extends BaseFragment {
//    @Override
//    public int getLayoutResource() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
public abstract class BaseStatisticsFragment<T extends BasePresenter, E extends BaseModel> extends Fragment {
    public T mPresenter;
    public E mModel;
    public RxManager mRxManager;
    protected View rootView;
    Unbinder bind;
    private boolean isLastVisible = false;
    private boolean hidden = false;
    private boolean isFirst = true;
    private boolean isResuming = false;
    private boolean isViewDestroyed = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        mRxManager = new RxManager();
        bind = ButterKnife.bind(this, rootView);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        if (mPresenter != null) {
            mPresenter.mContext = this.getActivity();
        }
        initView();
        initPresenter();
        hideBottomUIMenu();
        hasNavBar( mPresenter.mContext);
        return rootView;
    }
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu(){
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v =this.getActivity().getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = this.getActivity().getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            this.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            // check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     * @return
     */
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }

    //获取布局文件
    protected abstract int getLayoutResource();

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    public abstract void initPresenter();

    //初始化view
    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
        bind.unbind();
        if (mPresenter != null)
            mPresenter.onDestroy();
        mRxManager.clear();
        isViewDestroyed = true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLastVisible = false;
        hidden = false;
        isFirst = true;
        isViewDestroyed = false;

    }

    @Override
    public void onResume() {
        super.onResume();
        isResuming = true;
        tryToChangeVisibility(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        isResuming = false;
        tryToChangeVisibility(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setUserVisibleHintClient(isVisibleToUser);
    }

    private void setUserVisibleHintClient(boolean isVisibleToUser) {
        tryToChangeVisibility(isVisibleToUser);
        if (isAdded()) {
            // 当Fragment不可见时，其子Fragment也是不可见的。因此要通知子Fragment当前可见状态改变了。
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseStatisticsFragment) {
                        ((BaseStatisticsFragment) fragment).setUserVisibleHintClient(isVisibleToUser);
                    }
                }
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        onHiddenChangedClient(hidden);
    }

    public void onHiddenChangedClient(boolean hidden) {
        this.hidden = hidden;
        tryToChangeVisibility(!hidden);
        if (isAdded()) {
            List<Fragment> fragments = getChildFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseStatisticsFragment) {
                        ((BaseStatisticsFragment) fragment).onHiddenChangedClient(hidden);
                    }
                }
            }
        }
    }

    private void tryToChangeVisibility(boolean tryToShow) {
        // 上次可见
        if (isLastVisible) {
            if (tryToShow) {
                return;
            }
            if (!isFragmentVisible()) {
                onFragmentPause();
                isLastVisible = false;
            }
            // 上次不可见
        } else {
            boolean tryToHide = !tryToShow;
            if (tryToHide) {
                return;
            }
            if (isFragmentVisible()) {
                onFragmentResume(isFirst, isViewDestroyed);
                isLastVisible = true;
                isFirst = false;
            }
        }
    }

    /**
     * Fragment是否可见
     *
     * @return
     */
    public boolean isFragmentVisible() {
        if (isResuming()
                && getUserVisibleHint()
                && !hidden) {
            return true;
        }
        return false;
    }

    /**
     * Fragment 是否在前台。
     *
     * @return
     */
    private boolean isResuming() {
        return isResuming;
    }

    /**
     * Fragment 可见时回调
     *
     * @param isFirst         是否是第一次显示
     * @param isViewDestroyed Fragment中的View是否被回收过。
     *                        存在这种情况：Fragment 的 View 被回收，但是Fragment实例仍在。
     */
    protected void onFragmentResume(boolean isFirst, boolean isViewDestroyed) {
        MobclickAgent.onPageStart(getClass().getSimpleName()); //统计页面("MainScreen"为页面名称，可自定义)
    }

    /**
     * Fragment 不可见时回调
     */
    protected void onFragmentPause() {

        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }
}
