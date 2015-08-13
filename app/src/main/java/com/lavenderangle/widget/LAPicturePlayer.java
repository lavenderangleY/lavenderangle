//package com.lavenderangle.widget;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Parcelable;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewParent;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.lavenderangle.program.R;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//
///**
// * 图片轮播组件
// * Created by disanyun on 2015/8/12 0012.
// */
//public class LAPicturePlayer extends FrameLayout{
//    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
//    //private ImageLoader imageLoader = ImageLoader.getInstance();
//    // 自动轮播启用开关
//    private static boolean isAutoPlay = true;
//
//    // 自定义轮播图的资源
//    public List<String> imageUrls;
//    // 放轮播图片的ImageView 的list
//    private List<ImageView> imageViewsList;
//    // 图片的点击事件
//    public List<OnClickListener> imageViewClickListener;
//    // 放圆点的View的list
//    public List<ImageView> dotViewsList;
//
//    private ViewPager viewPager;
//    // 当前轮播页
//    private int currentItem = 0;
//    // 定时任务
//    private ScheduledExecutorService scheduledExecutorService;
//
//    private Context context;
//
//    private ImageView imageView;
//
//    private boolean isFrist=true;
//
//    private LinearLayout dotLayout ;
//
//    // Handler
//    private Handler handler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (isAutoPlay) {
//                viewPager.setCurrentItem(currentItem);
//            }
//        }
//
//    };
//
//    public LAPicturePlayer(Context context) {
//        this(context, null);
//    }
//
//    public LAPicturePlayer(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public LAPicturePlayer(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        this.context = context;
//        initImageLoader(context);
//        initData();
//    }
//
//    public int getCurrentItem() {
//        return currentItem;
//    }
//
//    public void setCurrentItem(int currentItem) {
//        this.currentItem = currentItem;
//    }
//
//    /**
//     * 开始轮播图切换
//     */
//    public void startPlay() {
//        stopPlay();
//        initUI(context);
//        scheduledExecutorService = Executors
//                .newSingleThreadScheduledExecutor();
//        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),
//                4, 4, TimeUnit.SECONDS);
//    }
//
//    /**
//     * 停止轮播图切换
//     */
//    public void stopPlay() {
//        isAutoPlay = false;
//        if (scheduledExecutorService != null) {
//            scheduledExecutorService.shutdown();
//        }
//    }
//
//    /**
//     * 初始化相关Data
//     */
//    private void initData() {
//        imageViewsList = new ArrayList<ImageView>();
//        dotViewsList = new ArrayList<ImageView>();
//        imageUrls = new ArrayList<String>();
//        imageViewClickListener = new ArrayList<View.OnClickListener>();
//    }
//
//    /**
//     * 初始化Views等UI
//     */
//    private void initUI(Context context) {
//        if (imageUrls == null || imageUrls.size() == 0)
//            return;
//        if(imageUrls.size()==1){
//            isAutoPlay=false;
//        }else{
//            isAutoPlay = true;
//        }
//
//        if (isFrist) {
//            isFrist=false;
//            LayoutInflater.from(context).inflate(R.layout.item_guidegaller, this,
//                    true);
//            dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
//        }else{
//            dotLayout.removeAllViews();
//        }
//
//        imageViewsList.clear();
//        dotViewsList.clear();
//        // 热点个数与图片特殊相等
//        for (int i = 0; i < imageUrls.size(); i++) {
//            ImageView view = new ImageView(context);
//            view.setTag(imageUrls.get(i));
//
//            view.setImageResource(R.drawable.home_item_default_image);
//            view.setScaleType(ImageView.ScaleType.FIT_XY);
//            view.setOnClickListener(imageViewClickListener.get(i));
//            imageViewsList.add(view);
//
//            ImageView dotView = new ImageView(context);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//            if (i == 0 ) {
//                dotView.setImageResource(R.drawable.dot_selected);
//            }else{
//                dotView.setImageResource(R.drawable.dot_unselected);
//            }
//            params.leftMargin = 4;
//            params.rightMargin = 4;
//            params.weight = 1;
//            dotLayout.addView(dotView, params);
//            dotViewsList.add(dotView);
//        }
//        //==============================解决手动向左滑动时的图片位置空白问题（仅限于轮播个数为1或者2时）
//        int multiple = 0;
//        switch (imageUrls.size()) {
//            case 1:
//                multiple = 2;
//                break;
//            case 2:
//                multiple = 1;
//                break;
//        }
//        for (int j = 0; j < multiple; j++) {
//            for (int i = 0; i < imageUrls.size(); i++) {
//                ImageView view = new ImageView(context);
//                view.setTag(imageUrls.get(i));
//
//                view.setImageResource(R.drawable.home_item_default_image);
//                view.setScaleType(ScaleType.FIT_XY);
//                view.setOnClickListener(imageViewClickListener.get(i));
//                imageViewsList.add(view);
//            }
//        }
//        //===============================
//        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        viewPager.setFocusable(true);
//        viewPager.setAdapter(new MyPagerAdapter());
//        viewPager.setCurrentItem(0);
//        currentItem = 0;
//        viewPager.setOnPageChangeListener(new MyPageChangeListener());
//    }
//
//    /**
//     * 填充ViewPager的页面适配器
//     *
//     */
//    private class MyPagerAdapter extends PagerAdapter {
//
//        @Override
//        public void destroyItem(View container, int position, Object object) {
//            // position = position % imageViewsList.size();
//            //((ViewPager) container).removeView(imageViewsList.get(position));
//        }
//
//        @Override
//        public Object instantiateItem(View container, int position) {
//            position = position % imageViewsList.size();
//            ViewParent parent = imageViewsList.get(position).getParent();
//            if (parent!=null){
//                ViewGroup vg = (ViewGroup)parent;
//                vg.removeView(imageViewsList.get(position));
//            }
//            imageView = imageViewsList.get(position);
//            imageLoader.displayImage(imageView.getTag() + "", imageView);
//
//            ((ViewPager) container).addView(imageViewsList.get(position));
//            return imageViewsList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return Integer.MAX_VALUE;
//            //return imageViewsList.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View arg0, Object arg1) {
//            return arg0 == arg1;
//        }
//
//        @Override
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {
//
//        }
//
//        @Override
//        public Parcelable saveState() {
//            return null;
//        }
//
//        @Override
//        public void startUpdate(View arg0) {
//
//        }
//
//        @Override
//        public void finishUpdate(View arg0) {
//
//        }
//
//    }
//
//    /**
//     * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
//     *
//     */
//    private class MyPageChangeListener implements OnPageChangeListener {
//
//        //boolean isAutoPlay = false;
//
//        @Override
//        public void onPageScrollStateChanged(int arg0) {
//            switch (arg0) {
//                case 1:// 手势滑动，空闲中
//                    isAutoPlay = false;
//                    break;
//                case 2:// 界面切换中
//                    isAutoPlay = false;
//                    break;
//                case 0:// 滑动结束，即切换完毕或者加载完毕
//                    if(imageUrls.size()==1){
//                        isAutoPlay=false;
//                    }else{
//                        isAutoPlay = true;
//                    }
//                    break;
//            }
//        }
//
//        @Override
//        public void onPageScrolled(int arg0, float arg1, int arg2) {
//        }
//
//        @Override
//        public void onPageSelected(int pos) {
//            currentItem = pos;
//            //currentItem = pos;
//            int position =  pos % dotViewsList.size();
//            for (int i = 0; i < dotViewsList.size(); i++) {
//                if (i == position) {
//                    ((ImageView) dotViewsList.get(position))
//                            .setImageResource(R.drawable.dot_selected);
//                } else {
//                    ((ImageView) dotViewsList.get(i))
//                            .setImageResource(R.drawable.dot_unselected);
//                }
//            }
//
//        }
//
//    }
//
//    /**
//     * 执行轮播图切换任务
//     *
//     */
//    private class SlideShowTask implements Runnable {
//
//        @Override
//        public void run() {
//            synchronized (viewPager) {
//                //currentItem = (currentItem + 1) % imageViewsList.size();
//                if (isAutoPlay) {
//                    currentItem++;
//                    handler.obtainMessage().sendToTarget();
//                }
//            }
//        }
//
//    }
//
//    /**
//     * 销毁ImageView资源，回收内存
//     *
//     */
//    private void destoryBitmaps() {
//        for (int i = 0; i < imageViewsList.size(); i++) {
//            ImageView imageView = imageViewsList.get(i);
//            Drawable drawable = imageView.getDrawable();
//            if (drawable != null) {
//                // 解除drawable对view的引用
//                drawable.setCallback(null);
//            }
//        }
//    }
//
//    /**
//     * ImageLoader 图片组件初始化
//     *
//     * @param context
//     */
//
//    private void initImageLoader(Context context) {
//        /*DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//                .imageScaleType(ImageScaleType.EXACTLY)//图像将完全按比例缩小的目标大小
//                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
//                .bitmapConfig(Bitmap.Config.RGB_565)
//                .build();
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                context).threadPriority(Thread.NORM_PRIORITY - 2)
//                .defaultDisplayImageOptions(defaultOptions)
//                .threadPoolSize(3)
//                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new WeakMemoryCache())
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .denyCacheImageMultipleSizesInMemory()
//                .writeDebugLogs()
//                .build();
//        // Initialize ImageLoader with configuration.
//        if (!imageLoader.isInited()) {
//            imageLoader.init(config);
//        }*/
//    }
//
//
//
//}
