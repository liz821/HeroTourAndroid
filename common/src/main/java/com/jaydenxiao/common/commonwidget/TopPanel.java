package com.jaydenxiao.common.commonwidget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaydenxiao.common.R;


/**
 * lzz
 */
public class TopPanel extends RelativeLayout {
    public TextView titleText;
    public TextView right_text;
    public ImageView leftBtn;
    public ImageView rightBtn;
    public LinearLayout title_container;
    ImageView line;
    onButtonClickListener onButtonClickListener;
    private Context context;
    private LinearLayout topPanel;
    private String left_type = "None";
    private String right_type = "None";
    private String title = "title";
    private boolean showLine = true;

    public TopPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // 获得自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopPanel);

        int count = ta.getIndexCount();
        for (int i = 0; i < count; i++) {
            int itemId = ta.getIndex(i); // 获取某个属性的Id值
            if (itemId == R.styleable.TopPanel_left_type) {
                left_type = ta.getString(itemId);

            } else if (itemId == R.styleable.TopPanel_right_type) {
                right_type = ta.getString(itemId);

            } else if (itemId == R.styleable.TopPanel_title) {
                title = ta.getString(itemId);

            } else if (itemId == R.styleable.TopPanel_bottom_line) {
                showLine = ta.getBoolean(itemId, true);

            } else {
            }
            initView();
        }
    }

    public TopPanel(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    public String getLeft_type() {
        return left_type;
    }

    public String getRight_type() {
        return right_type;
    }

    public void initView() {
        topPanel = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.top_panel, null);
        titleText = topPanel.findViewById(R.id.tvTitle);
        leftBtn = topPanel.findViewById(R.id.left_button);
        rightBtn = topPanel.findViewById(R.id.right_button);
        right_text = topPanel.findViewById(R.id.right_text);
        title_container = topPanel.findViewById(R.id.title_container);
        line = topPanel.findViewById(R.id.line);
        this.addView(topPanel, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        switch (left_type) {
            case "back":
                leftBtn.setVisibility(VISIBLE);
                leftBtn.setImageResource(R.drawable.back_bold48);
                break;
            case "search":
                leftBtn.setVisibility(VISIBLE);
                leftBtn.setImageResource(R.drawable.search);
                break;
            default:
                break;

        }
        switch (right_type) {
            case "share":
                rightBtn.setVisibility(VISIBLE);
                rightBtn.setImageResource(R.drawable.share);
                break;
            case "search":
                rightBtn.setVisibility(VISIBLE);
                rightBtn.setImageResource(R.drawable.search);
                break;
            case "text":
                right_text.setVisibility(VISIBLE);
                break;
            default:
                break;

        }
        if (!showLine) {
            line.setVisibility(GONE);
        }
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onLeftClick();
            }
        });
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickListener.onRightClick();
            }
        });
        titleText.setText(title);
    }

    /**
     * 设置按钮点击事件
     */
    public void setOnButtonClickLisener(onButtonClickListener lisener) {
        this.onButtonClickListener = lisener;
    }

    public interface onButtonClickListener {
        void onLeftClick();

        void onRightClick();
    }
}
