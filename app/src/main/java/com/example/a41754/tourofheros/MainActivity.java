package com.example.a41754.tourofheros;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.a41754.tourofheros.fragment.LoggerFragment;
import com.example.a41754.tourofheros.fragment.DashFragment;
import com.example.a41754.tourofheros.fragment.DetailFragment;
import com.example.a41754.tourofheros.fragment.ListFragment;
import com.jaydenxiao.common.base.BaseActivity;

import java.util.Map;

import butterknife.OnClick;
import io.reactivex.functions.Consumer;


public class MainActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    Fragment[] fragments = new Fragment[3];
    DashFragment fragment1;
    ListFragment fragment2;
    DetailFragment fragment3;
    LoggerFragment fragment4;

    FragmentTransaction fragmentTransaction;

    @Override
    public void initView() {
        fragment1 = new DashFragment();
        fragment2 = new ListFragment();
        fragment3 = new DetailFragment();

        fragment4 = new LoggerFragment();


        fragments[0] = fragment1;
        fragments[1] = fragment2;
        fragments[2] = fragment3;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container1, fragment1, "fragment1")
                .add(R.id.container1, fragment2, "fragment2")
                .add(R.id.container1, fragment3, "fragment3")
                .add(R.id.container2, fragment4, "fragment4")
                .hide(fragment3).hide(fragment2)
                .commit();



        mRxManager.on(Constants.EVENT_SWITCHDETAIL, new Consumer<Integer>() {
            @Override
            public void accept(Integer toIndex) throws Exception {
                current_index=toIndex;
                switchContent(fragments[last_index], fragments[current_index]);
                last_index=toIndex;
            }
        });

    }

    public void switchContent(Fragment from, Fragment to) {
        if (from != to) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();

            if (to != null && !to.isAdded()) {    // 先判断是否被add过
                fragmentTransaction.hide(from).add(R.id.container1, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                fragmentTransaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }

    }



    int current_index = 0;
    int last_index = 0;
    @OnClick({R.id.buttonDash, R.id.buttonList})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonDash:
                current_index = 0;
                switchContent(fragments[last_index], fragments[current_index]);
                last_index = 0;
                break;
            case R.id.buttonList:
                current_index = 1;
                switchContent(fragments[last_index], fragments[current_index]);
                last_index = 1;
                break;
        }
    }


}

