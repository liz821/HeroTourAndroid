package com.example.a41754.tourofheros.adpters;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.R;
import com.example.a41754.tourofheros.bean.LoggerBean;

import java.util.logging.Logger;


/**
 * Created by lzz on 2018/6/21.
 */

public class LoggerAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public LoggerAdapter() {
        super(R.layout.item_logger, null);
    }



    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv,item);
    }
}
