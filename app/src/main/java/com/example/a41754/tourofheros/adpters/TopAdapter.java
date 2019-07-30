package com.example.a41754.tourofheros.adpters;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.R;


/**
 * Created by lzz on 2018/6/21.
 */

public class TopAdapter extends BaseQuickAdapter<HeroBean,BaseViewHolder> {

    public TopAdapter() {
        super(R.layout.item_top, null);
    }



    @Override
    protected void convert(BaseViewHolder helper, HeroBean item) {
        helper.setText(R.id.topTV,item.getName());

    }
}
