package com.example.a41754.tourofheros.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a41754.tourofheros.Constants;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.R;
import com.example.a41754.tourofheros.adpters.TopAdapter;
import com.example.a41754.tourofheros.db.DaoManager;
import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDao;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class DashFragment extends BaseFragment {
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.searcher)
    EditText searcher;
    @BindView(R.id.topHeroRV)
    RecyclerView topHeroRV;

    @Override
    protected int getLayoutResource() {
        return R.layout.dash_layout1;
    }

    @Override
    public void initPresenter() {

    }

    TopAdapter topAdapter;
    ArrayList<HeroBean> heroBeans;
    PopupMenu popupMenu;
    Menu menu;

    @Override
    protected void initView() {
        popupMenu = new PopupMenu(getContext(), searcher);
        menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(item -> {
            for (int i = 0; i < list.size(); i++)
                if (list.get(i).getName().equals(item.getTitle().toString()))
                    viewDetail("" + list.get(i).get_id(), list.get(i).getName());
            return false;
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        topHeroRV.setLayoutManager(gridLayoutManager);

        topAdapter = new TopAdapter();
        topAdapter.bindToRecyclerView(topHeroRV);
        queryAndSetData();

        topAdapter.setOnItemClickListener((adapter, view, position) -> {
            HeroBean heroBean = heroBeans.get(position);
            viewDetail("" + heroBean.getId(), heroBean.getName());

        });
        searcher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchDB(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRxManager.on(Constants.EVENT_DATACHANGE, new Consumer<String>() {
            @Override
            public void accept(String from) throws Exception {
                if (!from.equals(DashFragment.class.getName()))
                    queryAndSetData();
            }
        });
    }

    private void queryAndSetData() {
        List<Hero> list = DaoManager.getInstance().getDaoSession().getHeroDao().queryBuilder().list();
        if (list.size() == 0)
            ToastUitl.showShort("暂无英雄,请添加");
        else {
            heroBeans = new ArrayList<>();
            for (int i = 0; i < list.size() && i < 4; i++) {
                Hero hero = list.get(i);
                HeroBean heroBean = new HeroBean();
                heroBean.setId(Integer.parseInt(String.valueOf(hero.get_id())));
                heroBean.setName(hero.getName());
                heroBeans.add(heroBean);
            }
            topAdapter.setNewData(heroBeans);
        }
    }

    HeroDao heroDao;
    List<Hero> list;

    void viewDetail(String id, String name) {

        mRxManager.post(Constants.EVENT_VIEWDETAIL, new String[]{"0", "" + id
                , name});
    }

    void searchDB(String key) {
        if (heroDao == null)
            heroDao = DaoManager.getInstance().getDaoSession().getHeroDao();
        list = heroDao.queryBuilder().where(HeroDao.Properties.Name.like("%" + key + "%")).list();
        menu.clear();
        menu.close();
        if (list.size() == 0)
            ToastUitl.showShort("无匹配项");
        else {
            for (int i = 0; i < list.size(); i++)
                menu.add(list.get(i).getName());
            popupMenu.show();
        }

    }
}
