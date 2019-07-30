package com.example.a41754.tourofheros.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a41754.tourofheros.Constants;
import com.example.a41754.tourofheros.R;
import com.example.a41754.tourofheros.adpters.LoggerAdapter;
import com.jaydenxiao.common.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class LoggerFragment extends BaseFragment {
    @BindView(R.id.rvLog)
    RecyclerView rvLog;
    @BindView(R.id.root)
    LinearLayout root;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_log;
    }

    @Override
    public void initPresenter() {

    }

    ArrayList<String> logs;
    LoggerAdapter adapter;

    @Override
    protected void initView() {
        rvLog.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LoggerAdapter();
        adapter.bindToRecyclerView(rvLog);
        clean();

        mRxManager.on(Constants.EVENT_LOGCHANGE, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                logs.add(s);
                adapter.notifyDataSetChanged();
                root.setVisibility(View.VISIBLE);
                rvLog.smoothScrollToPosition(logs.size() - 1);

            }
        });

    }

    void clean() {
        logs = new ArrayList<>();
        adapter.setNewData(logs);
        root.setVisibility(View.INVISIBLE);

    }

    @OnClick(R.id.bt)
    public void onClick() {
        clean();
    }
}
