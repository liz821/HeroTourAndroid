package com.example.a41754.tourofheros.adpters;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a41754.tourofheros.bean.HeroBean;
import com.example.a41754.tourofheros.R;


/**
 * Created by lzz on 2018/6/21.
 */

public class ListAdapter extends BaseQuickAdapter<HeroBean,BaseViewHolder> {

    public ListAdapter() {
        super(R.layout.item_list, null);
    }



    @Override
    protected void convert(BaseViewHolder helper, HeroBean item) {
        helper.setText(R.id.id,""+item.getId()).setText(R.id.name,item.getName()).addOnClickListener(R.id.delete);
    }
}
