package com.lavenderangle.program;

import android.opengl.GLSurfaceView;

import com.lavenderangle.widget.CustomRenderer;

public class MainActivity extends BaseActivity {

    @Override
    public Object getContentView() {
        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(new CustomRenderer());
        return glSurfaceView;
    }

    @Override
    protected void initViews() {
        //showProgressLoading(true);

    }

    @Override
    protected void initData() {

    }
}
