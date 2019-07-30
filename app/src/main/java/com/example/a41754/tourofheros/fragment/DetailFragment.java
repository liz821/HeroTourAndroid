package com.example.a41754.tourofheros.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a41754.tourofheros.Constants;
import com.example.a41754.tourofheros.R;
import com.example.a41754.tourofheros.db.DaoManager;
import com.example.a41754.tourofheros.db.Hero;
import com.example.a41754.tourofheros.db.HeroDao;
import com.jaydenxiao.common.base.BaseFragment;
import com.jaydenxiao.common.commonutils.ToastUitl;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class DetailFragment extends BaseFragment {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.heroId)
    TextView heroId;
    @BindView(R.id.edName)
    EditText edName;
    @BindView(R.id.back)
    Button back;

    @Override
    protected int getLayoutResource() {
        return R.layout.hero_detail;
    }

    @Override
    public void initPresenter() {

    }

    int from;
    int __id;
    String __name;

    @Override
    protected void initView() {
        mRxManager.on(Constants.EVENT_VIEWDETAIL, new Consumer<String[]>() {
            @Override
            public void accept(String[] strs) throws Exception {
                from = Integer.parseInt(strs[0]);
                __id = Integer.parseInt(strs[1]);
                __name = strs[2];
                title.setText(String.format("%s Detail", __name));
                edName.setText(__name);
                heroId.setText(String.valueOf(__id));
                mRxManager.post(Constants.EVENT_SWITCHDETAIL, 2);
                mRxManager.post(Constants.EVENT_LOGCHANGE, "fetch id:" + __id + " name:" + __name);
            }
        });
    }


    @OnClick({R.id.back, R.id.save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                mRxManager.post(Constants.EVENT_SWITCHDETAIL, from);

                break;
            case R.id.save:
                if (!TextUtils.isEmpty(edName.getText())) {
                    String newName = edName.getText().toString();
                    save(__id, newName);
                    mRxManager.post(Constants.EVENT_SWITCHDETAIL, from);
                     mRxManager.post(Constants.EVENT_DATACHANGE,DetailFragment.class.getName());
                    mRxManager.post(Constants.EVENT_LOGCHANGE, "update: ID:" + __id + "name:" + __name + "->" + newName);
                } else ToastUitl.showShort("姓名不能为空");
                break;
        }
    }

    void save(int id, String name) {
        HeroDao heroDao = DaoManager.getInstance().getDaoSession().getHeroDao();
        Hero unique = heroDao.queryBuilder().where(HeroDao.Properties._id.eq(id)).unique();
        unique.setName(name);
        heroDao.update(unique);
    }
}
