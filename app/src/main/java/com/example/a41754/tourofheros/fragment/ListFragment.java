package com.example.a41754.tourofheros.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a41754.tourofheros.Constants;
import com.example.a41754.tourofheros.PublicMethods;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.R;
import com.example.a41754.tourofheros.adpters.ListAdapter;
import com.example.a41754.tourofheros.db.DaoManager;
import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDao;
import com.example.a41754.tourofheros.db.HeroDaoUtil;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class ListFragment extends BaseFragment {
    @BindView(R.id.ed_add_name)
    EditText edAddName;
    @BindView(R.id.rvList)
    RecyclerView rvList;

    @Override
    protected int getLayoutResource() {
        return R.layout.heros_layout1;
    }

    @Override
    public void initPresenter() {

    }

    ArrayList<HeroBean> heroBeans = new ArrayList<>();

    ListAdapter listAdapter;

    @Override
    protected void initView() {
        mRxManager.on(Constants.EVENT_DATACHANGE, new Consumer<String>() {
            @Override
            public void accept(String from) throws Exception {
                if (!from.equals(ListFragment.class.getName()))
                    queryAndSetData(HeroDaoUtil.getAllData(), heroBeans, listAdapter);

            }
        });


        rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        listAdapter = new ListAdapter();
        listAdapter.bindToRecyclerView(rvList);

        queryAndSetData(HeroDaoUtil.getAllData(), heroBeans, listAdapter);
        listAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.delete:
                        delete(listAdapter,position, Long.valueOf("" + listAdapter.getItem(position).getId()));
                        ToastUitl.showShort("aaa  " + position);
                        break;
                }
            }
        });
        listAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                HeroBean heroBean = (HeroBean) adapter.getData().get(position);
                mRxManager.post(Constants.EVENT_VIEWDETAIL, new String[]{"1", "" + heroBean.getId()
                        , heroBean.getName()});
            }
        });

    }

    public void queryAndSetData(List<Hero> list, ArrayList<HeroBean> heroBeans, BaseQuickAdapter listAdapter) {
        if (list.size() == 0)
            ToastUitl.showShort("暂无英雄,请添加");
        else {
            heroBeans.clear();
            for (int i = 0; i < list.size(); i++) {
                Hero hero = list.get(i);
                HeroBean heroBean = new HeroBean();
                heroBean.setId(Integer.parseInt(String.valueOf(hero.get_id())));
                heroBean.setName(hero.getName());
                heroBeans.add(heroBean);
            }
            listAdapter.setNewData(heroBeans);
        }
    }


    void delete(ListAdapter listAdapter, int position, Long id) {
        listAdapter.remove(position);
        Hero unique = HeroDaoUtil.getHeroUniqueFromId(id);
        HeroDaoUtil.delete(unique);

        afterDelete(unique);
    }

    void afterDelete(Hero unique) {
        mRxManager.post(Constants.EVENT_DATACHANGE, ListFragment.class.getName());
        mRxManager.post(Constants.EVENT_LOGCHANGE, "delete  id:" + unique.get_id() + unique.getName());

    }

    void add(String name) {
        PublicMethods.hideKeyBoard(getContext(), getView());
        List<Hero> list = HeroDaoUtil.getHeroListFromName(name);
        if (list.size() == 0) {

            Hero hero = new Hero();
            hero.setName(name);
            HeroDaoUtil.insert(hero);

            HeroBean heroBean = new HeroBean();
            heroBean.setName(name);

            list = HeroDaoUtil.getHeroListFromName(name);
            int id = Integer.parseInt("" + list.get(0).get_id());
            heroBean.setId(id);

            afterAdd(heroBean, id, name);
        } else
            ToastUitl.showShort("已存在");
    }

    void afterAdd(HeroBean heroBean, int id, String name) {
        heroBeans.add(heroBean);
        listAdapter.notifyDataSetChanged();
        rvList.smoothScrollToPosition(listAdapter.getData().size() - 1);
        mRxManager.post(Constants.EVENT_DATACHANGE, ListFragment.class.getName());
        mRxManager.post(Constants.EVENT_LOGCHANGE, "add  id:" + id + name);
        edAddName.setText("");
    }

    @OnClick(R.id.add)
    public void onClick() {
        if (!TextUtils.isEmpty(edAddName.getText())) {
            add(edAddName.getText().toString());
        }

    }


}
