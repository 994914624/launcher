package com.yzk.launcher.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import androidx.core.widget.NestedScrollView;

/**
 * Created by yzk on 2020/8/15
 */

public class CustomNestedScrollView extends NestedScrollView {

    private int slop;
    private int touch;

    public CustomNestedScrollView(Context context) {
        super(context);
        setSlop(context);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setSlop(context);
    }

    public CustomNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setSlop(context);
    }

    /**
     * 是否intercept当前的触摸事件
     * @param ev 触摸事件
     * @return true：调用onMotionEvent()方法，并完成滑动操作
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //  保存当前touch的纵坐标值
                touch = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                //  滑动距离大于slop值时，返回true
                if (Math.abs((int) ev.getRawY() - touch) > slop) return true;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 获取相应context的touch slop值（即在用户滑动之前，能够滑动的以像素为单位的距离）
     * @param context ScrollView对应的context
     */
    private void setSlop(Context context) {
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
}
