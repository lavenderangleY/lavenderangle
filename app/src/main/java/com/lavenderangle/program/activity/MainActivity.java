package com.lavenderangle.program.activity;

import com.lavenderangle.program.R;

/**
 * Created by disanyun on 2015/8/19 0019.
 */
public class MainActivity extends BaseActivity {
    @Override
    public void beforeSetView() {
    }

    @Override
    public Object getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        throw  new NullPointerException("异常");
    }

    @Override
    protected void initData() {

    }
}
