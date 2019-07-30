package com.jaydenxiao.common.commonwidget;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenxiao.common.R;


/**
 * description:弹窗浮动加载进度条
 * Created by xsf
 * on 2016.07.17:22
 */
public class LoadingDialog {
    /**
     * 加载数据对话框
     */
    private volatile static Dialog mLoadingDialog;

    /**
     * 显示加载对话框
     *
     * @param context    上下文
     * @param msg        对话框显示内容
     * @param cancelable 对话框是否可以取消
     */
    public static synchronized Dialog showDialogForLoading(Activity context, String msg, boolean cancelable) {
        TextView loadingText;
        if (mLoadingDialog == null) {
            synchronized (LoadingDialog.class) {
                if (mLoadingDialog == null) {
                    mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);

                    View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
//            loadingText = view.findViewById(R.id.id_tv_loading_dialog_text);
                    mLoadingDialog.setCancelable(cancelable);
                    mLoadingDialog.setCanceledOnTouchOutside(false);
                    mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                }
            }

        }
        if (!mLoadingDialog.isShowing()) {
            synchronized (LoadingDialog.class) {
                loadingText = mLoadingDialog.getWindow().findViewById(R.id.id_tv_loading_dialog_text);
                loadingText.setText(msg);
                if (!mLoadingDialog.isShowing())
                mLoadingDialog.show();
            }
        }

        return mLoadingDialog;
    }

    public static Dialog showDialogForLoading(Activity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView loadingText = (TextView) view.findViewById(R.id.id_tv_loading_dialog_text);
        loadingText.setText("加载中...");

        mLoadingDialog = new Dialog(context, R.style.CustomProgressDialog);
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
        mLoadingDialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mLoadingDialog.show();
        return mLoadingDialog;
    }

    /**
     * 关闭加载对话框
     */
    public synchronized static void cancelDialogForLoading() {
        if (mLoadingDialog != null)
            mLoadingDialog.cancel();
    }
}
