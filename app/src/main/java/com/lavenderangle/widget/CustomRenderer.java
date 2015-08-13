package com.lavenderangle.widget;

import android.opengl.GLSurfaceView;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by disanyun on 2015/8/13 0013.
 */


public class CustomRenderer implements GLSurfaceView.Renderer {
    private FloatBuffer triangleDataBuffer;
    private IntBuffer triangleColorBuffer;
    private FloatBuffer rectDataBuffer;
    private IntBuffer rectColorBuffer;
    private FloatBuffer rectDataBuffer2;
    private FloatBuffer pentacleBuffer;

    private float[] triangleData = new float[]{
            0.1f , 0.6f , 0.0f , //上顶点
            -0.3f , 0.0f , 0.0f , //左顶点
            0.3f , 0.1f , 0.0f , //右顶点
    };

    private float[] rectData = new float[]{
            0.4f , 0.4f , 0.0f , //右上顶点
            0.4f , -0.4f , 0.0f , //右下顶点
            -0.4f , 0.4f , 0.0f , //左上顶点
            -0.4f , -0.4f , 0.0f , //左下顶点
    };

    private float[] rectData2 = new float[]{
            -0.4f , 0.4f , 0.0f , //左上顶点
            0.4f , 0.4f , 0.0f , //右上顶点
            0.4f , -0.4f , 0.0f , //右下顶点
            -0.4f , -0.4f , 0.0f , //左下顶点
    };

    private float[] pentacle = new float[]{
            0.4f , 0.4f , 0.0f ,
            -0.2f , 0.3f , 0.0f ,
            0.5f , 0.0f , 0.0f ,
            -0.4f , 0.0f , 0.0f ,
            -0.1f , -0.3f , 0.0f ,
    };


    private int[] triangleColor = new int[]{
            65535,0,0,0,//上顶点红色
            0,65535,0,0,//左顶点绿色
            0,0,65535,0 //右顶点蓝色
    };

    private int[] rectColor = new int[]{
            0,65535,0,0,//左上顶点绿色
            0,0,65535,0,//右下顶点蓝色
            65535,0,0,0,//左上顶点红色
            65535,65535,0,0 //左下顶点黄色
    };


    public CustomRenderer(){
        //将顶点位置数据数组包装成FloatBuffer
        triangleDataBuffer = FloatBuffer.wrap(triangleData);
        rectDataBuffer = FloatBuffer.wrap(rectData);
        rectDataBuffer2 = FloatBuffer.wrap(rectData2);
        pentacleBuffer = FloatBuffer.wrap(pentacle);
        //将顶点颜色数据数组包装成IntBuffer
        triangleColorBuffer = IntBuffer.wrap(triangleColor);
        rectColorBuffer = IntBuffer.wrap(rectColor);
    }

    /**
     * GLSurfaceView被创建时回调
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        //设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0,0,0,0);
        //设置阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    /**
     * 当GLSurfaceView的大小改变时回调
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //设置3D视窗的大小和位置
        gl.glViewport(0, 0, width, height);
        //将当前矩阵模式设置成投影矩阵,指定屏幕为透视图
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵，相当于reset
        gl.glLoadIdentity();
        //计算透视视窗的宽度和高度比
        float ratio = (float)width/height;
        //调用此方法设置透视视窗的空间大小
        gl.glFrustumf(-ratio,ratio,-1,1,1,10);
    }

    /**
     * Renderer对象调用该方法绘制GLSurfaceView的当前帧
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {
        //清除屏幕缓存和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        //启用顶点坐标数据
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //启用顶点颜色数据
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        //设置当前矩阵堆栈为模型堆栈
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //-------------------绘制第一个图形-------------------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.32f, 0.35f, -1f);
        //设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleDataBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, triangleColorBuffer);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        //--------------------绘制第二个图形--------------------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(0.6f, 0.8f, -1.5f);
        //设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer);
        //设置顶点的颜色数据
        gl.glColorPointer(4, GL10.GL_FIXED, 0, rectColorBuffer);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        //--------------------绘制第三个图形--------------------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.4f, -0.5f, -1.5f);
        //设置顶点的位置数据（依然使用之前的顶点颜色）
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectDataBuffer2);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        //--------------------绘制第四个图形--------------------
        //重置当前的模型视图矩阵
        gl.glLoadIdentity();
        gl.glTranslatef(-0.4f, -0.5f, -1.5f);
        //设置使用纯色填充
        gl.glColor4f(1.0f,0.2f,0.2f,0.0f);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        //设置顶点的位置数据
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, pentacleBuffer);
        //根据顶点数据绘制平面图形
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP,0,4);
        //绘制结束
        gl.glFinish();
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}

