package com.lavenderangle.program;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lavenderangle.dialog.ProgressLoading;

/**
 * Created by disanyun on 2015/8/13 0013.
 */
public abstract class BaseActivity extends Activity{

    protected final String TAG = "" + getClass().getSimpleName();
    protected ProgressLoading loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isSuccess = setContentView();
        if (isSuccess){
            initViews();
            initData();
        }
    }

    /**
     * 设置布局
     * @return true表示设置成功
     */
    private boolean setContentView() {
        Object object = getContentView();
        if (object instanceof Integer){
            int layoutID = (int) object;
            setContentView(layoutID);
            return true;
        }else if(object instanceof View){
            View layoutView = (View) object;
            setContentView(layoutView);
            return true;
        }else{
            Log.e(TAG,"getContentView() must return Integer type or View type");
        }
        return false;
    }

    /**
     * 设置当前Activity需要加载的布局，必须是int类型或者View类型
     * @return
     */
    public abstract Object getContentView();

    /**
     * 初始化控件信息
     */
    protected abstract void initViews();

    /**
     * 设置控件数据
     */
    protected abstract void initData();


    protected void showProgressLoading(boolean canCancel){
        if (loading == null){
            loading = new ProgressLoading(this);
        }
        if (!canCancel) {
            loading.setCancelable(false);
            loading.setCanceledOnTouchOutside(false);
        }
        loading.show();
    }

    protected void dismissProgressLoading(){
        if (loading != null && loading.isShowing()){
            loading.dismiss();
        }
    }
}
