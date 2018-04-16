package com.example.floatmenu;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2018/2/27.
 */

public class FloatMenu {
    /**
     * 保存于share悬浮球位置的key
     */
    public static final String LOGO_X_KEY = "FloatMenuX";
    public static final String LOGO_Y_KEY = "FloatMenuY";

    public static final String mSharedPreferences_Name = "FloatMenu";

    /**
     * 悬浮球在左边还是右边
     */
    public static final int LEFT = 0;
    public static final int RIGHT = 1;

    /**
     * 悬浮球在上下左右角
     */
    public static final int LEFT_TOP = 2;
    public static final int LEFT_BOTTOM = 3;
    public static final int RIGHT_TOP = 4;
    public static final int RIGHT_BOTTOM = 5;

    /**
     * 悬浮球位置x,y
     */
    private int mLogoX = 0;
    private int mLogoY = 0;

    /**
     * 手指在悬浮球里的位置
     */
    private float mInLogoX = 1;
    private float mInLogoY;

    /**
     * 悬浮球旋转速度
     */
    public static int mRotate_time;

    private int R;                          //悬浮球直径
    private int mChildR;                    //子菜单的直径
    private int mOpenChildMenuR;            //子菜单展开的半径
    private int mChildPosition;             //子菜单位置

    private final int LOGO_ROTATE_ANGLE = 720;//悬浮球翻转的角度
    private final int LOGO_ROTATE_TIME = 300; //悬浮球翻转的速度
    private final int LOGO_RECOVER_TIME = 400;//悬浮球恢复的速度
    private final int OPEN_MENU_TIME = 200;  //开关子菜单的速度

    private int mValue;//恢复悬浮球移动时的value
    private int mOffset;          //子菜单与悬浮窗的坐标偏移量

    public static int mScreenHeight = 0;       //屏幕高度
    public static int mScreenWidth = 0;        //屏幕宽度
    public static int mStatusBarHeight = 0;    //状态栏高度

    private boolean isMoving = false;     //悬浮球是否在移动
    private boolean isRotate = false;    //长按是否旋转悬浮球
    private boolean isRotating = false;  //是否正在旋转
    private boolean isOpen = false;      //菜单是否被打开
    private boolean openingTimer = false;//定时器是否正在开启
    private boolean isTimer = false;     //是否需要计时器
    private boolean isHiding = false;    //悬浮球是否在隐藏
    private boolean mHalf;               //展开的子菜单是否为半圆

    private List<Bitmap> mChildMenuItemList = new ArrayList<>();          //记录子菜单item
    private List<CircleImageView> mChildMenuList = new ArrayList<>();           //记录子菜单View
    private List<CloseChildMenuBean> mCloseChildMenuList = new ArrayList<>();   //记录关闭悬浮球,子菜单移动的距离

    private Context mContext;
    private FrameLayout mChildLayout;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager.LayoutParams mChildLayoutParams;
    private FrameLayout.LayoutParams mChildMenuLayoutParams;
    private DisplayMetrics mDisplayMetrics;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private AnimatorSet mAnimatorSet;
    private AnimatorSet mRecoverAnimatorSet;
    private ValueAnimator mTranslationAnimator;    //移动动画
    private ObjectAnimator mRotationAnimator;       //翻转动画
    private Interpolator mInterpolator = new LinearInterpolator();

    private ThreadPoolManager mRotationChildThread = ThreadPoolManager.getThreadPoolManager();

    private CountDownTimer mTimer;  //定时器，定时隐藏悬浮球
    private Bitmap mLogo;           //悬浮球Logo
    private Bitmap mOpenLogo;       //悬浮球打开后的logo
    public LogoView mLogoView;     //菜单View

    private OnMenuClickListener mOnMenuClickListener;

    public interface OnMenuClickListener{
        void onClick(int position, View view);
    }

    private View.OnTouchListener mOnTounchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mInLogoX = event.getX();
                    mInLogoY = event.getY();
                    //当悬浮球处于隐藏状态，激活悬浮球
                    if(isHiding){
                        showLogo();
                    }
                    //旋转悬浮球
                    if(isRotate) {
                        isRotating = true;
                        rotationFloatMenu();
                    }

                    //取消定时器
                    if(isTimer && openingTimer){
                        mTimer.cancel();
                        openingTimer = false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    //移动悬浮球
                    if (!isOpen) {
                        isMoving = true;
                        //在屏幕按下的坐标 - 在悬浮球上按下的坐标 = 悬浮球的坐标（左上角）
                        moveFloatMenu(event.getRawX() - mInLogoX, event.getRawY() - mInLogoY);
                    }
                    //旋转悬浮球
                    if(isRotate && !isRotating) {
                        isRotating = true;
                        rotationFloatMenu();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    /**
                     * 停止旋转动画
                     */
                    if (isRotating) {
                        isRotating = false;
                    }
                    /**
                     * 恢复悬浮球位置
                     */
                    if (isMoving && mLogoX !=0 && mLogoX != mScreenWidth) {
                        recoverFloatMenu();
                    }else{
                        isMoving = false;
                    }
                    /**
                     * 打开或关闭菜单
                     */
                    if (!isOpen  && !isMoving && !openingTimer) {
                        if(mHalf) {
                            openFloatMenu();
                        }else {
                            openRectangleMenu();
                        }
                    } else if (isOpen) {
                        closeFloatMenu();
                    }
                    break;
            }
            return true;
        }
    };

    /**
     * 旋转动画
     */
    private Runnable mRotationRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRotate && isRotating) {
                try {
                    mLogoView.startRotation();
                    Thread.sleep(mRotate_time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     *
     * @param builder
     */
    FloatMenu(Builder builder) {
        mContext = builder.mContext;
        mLogo = builder.mFloatMenuLogo;
        mChildMenuItemList = builder.mFloatMenuItems;
        mOpenLogo = builder.mFloatMenuOpenLogo;
        mOnMenuClickListener = builder.mOnMenuClickListener;
        isRotate = builder.isRotate;
        isTimer = builder.isTimer;
        mHalf = builder.mHalf;
        mRotate_time = builder.mRotate_time;

        initFloatMenu();
    }

    /**
     * 打开子菜单
     */
    protected void openFloatMenu() {
        isOpen = true;
        final int location = getFloatMenuLocation();
        if(mLogo != mOpenLogo){
            mLogoView.drawOpenLogo();
        }

        double avg_angle;                         //每个子菜单之间的角度
        double offset_angle = 0;                     //偏差角度
        double inAll_angle = getInAllAngle(location);//总共角度
        int x;               //目标平移的x坐标
        int y;               //目标平移的y坐标
        int distance;

        if (mChildMenuList.size() == 1) {
            avg_angle = 0;
        } else {
            avg_angle = inAll_angle / (mChildMenuList.size() - 1);
        }

        switch (location) {
            case LEFT:
                offset_angle = 90;
                break;
            case RIGHT:
                offset_angle = -90;
                break;
            case LEFT_TOP:
                offset_angle = 180;
                break;
            case LEFT_BOTTOM:
                offset_angle = 90;
                break;
            case RIGHT_TOP:
                offset_angle = -90;
                break;
            case RIGHT_BOTTOM:
                offset_angle = 0;
                break;
        }
        //记录关闭子菜单移动的距离
        if(mCloseChildMenuList.size() != 0){
            mCloseChildMenuList.clear();
        }
        //添加子菜单
        addChildMenuLayout(location);
        //初始化动画类
        mAnimatorSet = new AnimatorSet();
        CircleImageView circleImageView = null;
        for (int j = 0; j < mChildMenuList.size(); j++) {
            circleImageView = mChildMenuList.get(j);
            distance = inAll_angle == 180 ? mOpenChildMenuR
                    : dp2px(circleImageView.r * 2,mContext);
            //x,y子菜单在当前位置到目标位置的距离
            x = (int) (-distance * Math.cos((avg_angle * j + offset_angle) * Math.PI / 180));
            y = (int) (-distance * Math.sin((avg_angle * j + offset_angle) * Math.PI / 180));
            mCloseChildMenuList.add(new CloseChildMenuBean(x,y));
            mAnimatorSet.playTogether(ObjectAnimator.ofFloat(circleImageView,"translationX",
                    circleImageView.getTranslationX(),circleImageView.getTranslationX() + x),
                    ObjectAnimator.ofFloat(circleImageView,"translationY",
                            circleImageView.getTranslationY(),circleImageView.getTranslationY() + y));
        }

        mAnimatorSet.setDuration(OPEN_MENU_TIME);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                dismissFloatMenuListener();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setFloatMenuListener();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.start();
    }

    /**
     * 打开矩形菜单
     */
    private void openRectangleMenu(){
        isOpen = true;
        int x;               //目标平移的x坐标
        int y;               //目标平移的y坐标

        if(mLogo != mOpenLogo){
            mLogoView.drawOpenLogo();
        }

        //记录关闭子菜单移动的距离
        if(mCloseChildMenuList.size() != 0){
            mCloseChildMenuList.clear();
        }
        //添加矩形子菜单
        addRectangleMenuLayout(getCurrentLocation());
        //初始化动画类
        mAnimatorSet = new AnimatorSet();
        CircleImageView circleImageView = null;
        for (int j = 0; j < mChildMenuList.size(); j++) {
            circleImageView = mChildMenuList.get(j);
            //x,y子菜单在当前位置到目标位置的距离
            x = getCurrentLocation() == LEFT ? mOpenChildMenuR * j + 1 : -mOpenChildMenuR * j + 1;
            y = 0;
            mCloseChildMenuList.add(new CloseChildMenuBean(x,y));
            mAnimatorSet.playTogether(ObjectAnimator.ofFloat(circleImageView,"translationX",
                    circleImageView.getTranslationX(),circleImageView.getTranslationX() + x),
                    ObjectAnimator.ofFloat(circleImageView,"translationY",
                            circleImageView.getTranslationY(),circleImageView.getTranslationY() + y));
        }

        mAnimatorSet.setDuration(OPEN_MENU_TIME);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                dismissFloatMenuListener();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setFloatMenuListener();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.start();
    }

    /**
     * 关闭子菜单
     */
    private void closeFloatMenu() {
        isOpen = false;
        if(mLogo != mOpenLogo){
            mLogoView.drawCloseLogo();
        }

        int x,y;
        mAnimatorSet = new AnimatorSet();
        CircleImageView circleImageView = null;
        for (int i = 0;i < mChildMenuList.size();i++){
            circleImageView = mChildMenuList.get(i);
            x = mCloseChildMenuList.get(i).getX();
            y = mCloseChildMenuList.get(i).getY();
            mAnimatorSet.playTogether(ObjectAnimator.ofFloat(circleImageView,"translationX",
                    circleImageView.getTranslationX(),circleImageView.getTranslationX() - x),
                    ObjectAnimator.ofFloat(circleImageView,"translationY",
                            circleImageView.getTranslationY(),circleImageView.getTranslationY() - y));
        }

        mAnimatorSet.setDuration(OPEN_MENU_TIME);
        mAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                dismissFloatMenuListener(); //取消悬浮球监听
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                removeChildMenuLayout();    //移除子菜单
                setFloatMenuListener();     //设置悬浮球监听
                if(isTimer && !openingTimer){
                    mTimer.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorSet.start();
    }

    /**
     * 添加半圆子菜单
     */
    private void addChildMenuLayout(int location){
        switch (location) {
            case LEFT:
                mChildLayoutParams.x = mLogoX + mOffset;
                mChildLayoutParams.y = mLogoY - R * 2 + R / 2;
                mChildLayoutParams.width = R * 2;
                mChildLayoutParams.height = R * 4;
                mChildMenuLayoutParams.gravity = Gravity.LEFT| Gravity.CENTER;
                break;
            case RIGHT:
                mChildLayoutParams.x = mLogoX - R * 2 - mOffset;
                mChildLayoutParams.y = mLogoY - R * 2 + R / 2;
                mChildLayoutParams.width = R * 2;
                mChildLayoutParams.height = R * 4;
                mChildMenuLayoutParams.gravity = Gravity.RIGHT | Gravity.CENTER;
                break;
            case LEFT_TOP:
                mChildLayoutParams.x = mLogoX + mOffset;
                mChildLayoutParams.y = mLogoY + mOffset;
                mChildLayoutParams.width = mChildR * 4;
                mChildLayoutParams.height = mChildR * 4;
                mChildMenuLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                break;
            case LEFT_BOTTOM:
                mChildLayoutParams.x = mLogoX + mOffset;
                mChildLayoutParams.y = mLogoY - mChildR * 4 + R - mOffset;
                mChildLayoutParams.width = mChildR * 4;
                mChildLayoutParams.height = mChildR * 4;
                mChildMenuLayoutParams.gravity = Gravity.LEFT | Gravity.BOTTOM;
                break;
            case RIGHT_TOP:
                mChildLayoutParams.x = mLogoX - mChildR * 4 - mOffset;
                mChildLayoutParams.y = mLogoY + mOffset;
                mChildLayoutParams.width = mChildR * 4;
                mChildLayoutParams.height = mChildR * 4;
                mChildMenuLayoutParams.gravity = Gravity.RIGHT | Gravity.TOP;
                break;
            case RIGHT_BOTTOM:
                mChildLayoutParams.x = mLogoX - mChildR * 4 - mOffset;
                mChildLayoutParams.y = mLogoY - mChildR * 4 + R - mOffset;
                mChildLayoutParams.width = mChildR * 4;
                mChildLayoutParams.height = mChildR * 4;
                mChildMenuLayoutParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;
                break;
        }

        for(CircleImageView c : mChildMenuList){
            mChildLayout.addView(c,mChildMenuLayoutParams);
        }
        mWindowManager.addView(mChildLayout,mChildLayoutParams);
    }

    /**
     * 添加矩形子菜单
     * @param location
     */
    private void addRectangleMenuLayout(int location){
        switch (location){
            case LEFT:
                mChildLayoutParams.x = mLogoX + R;
                mChildMenuLayoutParams.gravity = Gravity.LEFT | Gravity.CENTER;
                break;
            case RIGHT:
                mChildLayoutParams.x = mLogoX - 6 * R;
                mChildMenuLayoutParams.gravity = Gravity.RIGHT | Gravity.CENTER;
                break;
        }
        mChildLayoutParams.y = mLogoY + mOffset;
        mChildLayoutParams.width = mOpenChildMenuR * mChildMenuList.size();
        mChildLayoutParams.height = mChildR;

        for(CircleImageView c : mChildMenuList){
            mChildLayout.addView(c,mChildMenuLayoutParams);
        }
        mWindowManager.addView(mChildLayout,mChildLayoutParams);
    }

    /**
     * 移除子菜单
     */
    private void removeChildMenuLayout(){
        mChildLayout.removeAllViews();
        mWindowManager.removeView(mChildLayout);
    }

    /**
     * 可以展开子菜单的最大角度
     * @param location
     * @return
     */
    protected int getInAllAngle(int location) {
        if (location == LEFT || location == RIGHT) {
            return 180;
        } else {
            return 90;
        }
    }

    /**
     * 移动悬浮球
     */
    private void moveFloatMenu(float x, float y) {
        if (y <= mStatusBarHeight) {
            y = mStatusBarHeight;
        }
        mLogoX = (int) x;
        mLogoY = (int) y;

        if(mLogoX < 0){
            mLogoX = 0;
        }
        if(mLogoX >= mScreenWidth - R){
            mLogoX = mScreenWidth;
        }

        mLayoutParams.x = mLogoX;
        mLayoutParams.y = mLogoY;
        mWindowManager.updateViewLayout(mLogoView, mLayoutParams);
    }

    /**
     * 显示悬浮球
     */
    private void showLogo(){
        mAnimatorSet = new AnimatorSet();
        if(getCurrentLocation() == LEFT) {
            mAnimatorSet.play(ObjectAnimator.ofFloat(mLogoView,"translationX",
                    mLogoView.getX(), mLogoView.getX() + mLogoView.r * 3/2));
        }else{
            mAnimatorSet.play(ObjectAnimator.ofFloat(mLogoView,"translationX",
                    mLogoView.getX(), mLogoView.getX() - mLogoView.r * 3/2));
        }
        mAnimatorSet.setDuration(100);
        mAnimatorSet.start();
        isHiding = false;
    }

    /**
     * 隐藏悬浮球
     */
    private void hideLogo(){
        mAnimatorSet = new AnimatorSet();
        if(getCurrentLocation() == LEFT) {
            mAnimatorSet.play(ObjectAnimator.ofFloat(mLogoView,"translationX",
                    mLogoView.getX(), mLogoView.getX() - mLogoView.r * 3/2));
        }else{
            mAnimatorSet.play(ObjectAnimator.ofFloat(mLogoView,"translationX",
                    mLogoView.getX(), mLogoView.getX() + mLogoView.r * 3/2));
        }
        mAnimatorSet.setDuration(100);
        mAnimatorSet.start();
        isHiding = true;
    }

    /**
     * 旋转悬浮球
     */
    private void rotationFloatMenu() {
        mRotationChildThread.execute(mRotationRunnable);
    }

    /**
     * 恢复悬浮球位置
     */
    private void recoverFloatMenu() {
        final int location = getCurrentLocation();
        if(location == LEFT){
            mTranslationAnimator = ValueAnimator.ofInt(mLogoX,0);
        }else {
            mTranslationAnimator = ValueAnimator.ofInt(mLogoX,mScreenWidth);
        }
        mTranslationAnimator.setDuration(LOGO_RECOVER_TIME);
        mTranslationAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mValue = (int) valueAnimator.getAnimatedValue();
                updateLogo(mValue);
            }
        });
        mTranslationAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isMoving = false;
                if(isTimer && !isOpen){
                    mTimer.start();
                }
                saveLogoLocation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        /**
         * 是否有开启翻转动画
         */
        if(isRotate){
            mRecoverAnimatorSet = new AnimatorSet();
            mRecoverAnimatorSet.setInterpolator(mInterpolator);
            mRecoverAnimatorSet.playTogether(mTranslationAnimator,mRotationAnimator);
            mRecoverAnimatorSet.start();
        }else{
            mTranslationAnimator.setInterpolator(mInterpolator);
            mTranslationAnimator.start();
        }
    }

    /**
     * 更新悬浮球位置
     */
    private void updateLogo(int value){
        mLogoX = value;
        mLayoutParams.x = mLogoX;
        mWindowManager.updateViewLayout(mLogoView,mLayoutParams);
    }


    /**
     * 判断悬浮球在左半区还是右半区
     *
     * @return
     */
    private int getCurrentLocation() {
        if (mLogoX < mScreenWidth / 2) {
            return LEFT;
        } else {
            return RIGHT;
        }
    }

    /**
     * 获取悬浮球位置
     */
    private int getFloatMenuLocation() {
        if (mLogoX == 0 && mLogoY <= mChildR * 2) {
            return LEFT_TOP;
        }
        if (mLogoX == 0 && mLogoY >= mScreenHeight - mChildR * 2 - R) {
            return LEFT_BOTTOM;
        }
        if (mLogoX == mScreenWidth  && mLogoY <= mChildR * 2) {
            return RIGHT_TOP;
        }
        if (mLogoX == mScreenWidth && mLogoY >= mScreenHeight - mChildR * 2 - R) {
            return RIGHT_BOTTOM;
        }
        if (mLogoX == 0 && mLogoY >= mChildR * 2) {
            return LEFT;
        }
        if (mLogoX == mScreenWidth && mLogoY >= mChildR * 2) {
            return RIGHT;
        }
        return RIGHT;
    }

    /**
     * 保存上次悬浮球的位置
     */
    private void saveLogoLocation(){
        mEditor.putInt(LOGO_X_KEY,mLogoX);
        mEditor.putInt(LOGO_Y_KEY,mLogoY);
        mEditor.apply();
    }

    /**
     * 设置悬浮球监听
     */
    protected void setFloatMenuListener() {
        mLogoView.setOnTouchListener(mOnTounchListener);
    }

    /**
     * 取消悬浮球监听
     */
    protected void dismissFloatMenuListener() {
        mLogoView.setOnTouchListener(null);
    }

    public static int dp2px(int dp, Context context){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,context.getResources().getDisplayMetrics());
    }

    /**
     * 初始化悬浮球
     */
    private void initFloatMenu() {
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mDisplayMetrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);

        mLayoutParams = new WindowManager.LayoutParams();
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;

        setScreenWH();  //获取屏幕宽高和状态栏宽度

        mSharedPreferences = mContext.getSharedPreferences(mSharedPreferences_Name, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        mLogoView = new LogoView(mContext,mLogo);
        R = mLogoView.r * 2;
        /**
         * 获取上次悬浮球停留的位置
         */
        mLogoX = mSharedPreferences.getInt(LOGO_X_KEY, mLogoX);
        mLogoY = mSharedPreferences.getInt(LOGO_Y_KEY, mLogoY);

        if (mLogoX != 0 && mLogoX != mScreenWidth) {
            mLogoX = mScreenWidth;
            mLogoY = mScreenHeight / 3;
            mEditor.putInt(LOGO_X_KEY, mLogoX);
            mEditor.putInt(LOGO_Y_KEY, mLogoY);
            mEditor.apply();
        }

        /**
         * 获取上下文
         * 设置layoutParams属性
         */
        if(!(mContext instanceof Activity)){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                if(Build.VERSION.SDK_INT > 23){ //
                    mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }else{
                    mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//                    mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
//                            | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                }
            }else{
                    mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            }
        }

        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mLayoutParams.alpha = 1;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.x = mLogoX;
        mLayoutParams.y = mLogoY;

        /**
         * 初始化悬浮球
         */
        mLogoView.setOpenLogoBitmap(mOpenLogo);
        mLogoView.setOnTouchListener(mOnTounchListener);
        mWindowManager.addView(mLogoView, mLayoutParams);
        /**
         * 当存在子菜单时
         */
        if(mChildMenuItemList.size() != 0) {
            //初始化子菜单帧布局
            initChildLayout();
            //初始化子菜单
            initFloatChildMenu();
        }

        /**
         * 当允许开启定时器时
         */
        if(isTimer){
            initTimer();
        }
        //初始化恢复悬浮球动画
        initRecoverRotationAnimator();
    }

    /**
     * 初始化子菜单布局
     */
    private void initChildLayout(){
        mChildLayoutParams = new WindowManager.LayoutParams();
        mChildLayoutParams.format = PixelFormat.TRANSLUCENT;
        mChildLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        mChildLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        mChildLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mChildLayoutParams.alpha = 1;

        mChildLayout = new FrameLayout(mContext);
        mChildLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    closeFloatMenu();
                }
            }
        });
    }

    /**
     * 初始化子菜单
     */
    private void initFloatChildMenu() {
        CircleImageView circleImageView = null;
        for (int i = 0; i < mChildMenuItemList.size(); i++) {
            mChildPosition = i;
            circleImageView = new CircleImageView(mContext,mChildMenuItemList.get(i));
            setChildMenuClick(circleImageView,i);
            mChildMenuList.add(circleImageView);
        }
        mChildR = circleImageView.r * 2;
        mOpenChildMenuR = mHalf?dp2px(circleImageView.r,mContext) : dp2px((int)(mLogoView.r * 2/3.0),mContext);
        mOffset = mLogoView.r - circleImageView.r;
        mChildMenuLayoutParams = new FrameLayout.LayoutParams(circleImageView.r,circleImageView.r);
    }

    /**
     * 设置子菜单监听
     * @param view
     * @param i
     */
    private void setChildMenuClick(View view, final int i){
        if(mOnMenuClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = i;
                    mOnMenuClickListener.onClick(position,v);
                }
            });
        }
    }

    /**
     * 初始化定时器
     */
    private void initTimer(){
        mTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                openingTimer = true;
            }
            @Override
            public void onFinish() {
                hideLogo();
                if(!openingTimer){
                    mTimer.cancel();
                }
            }
        };
        if(isTimer && !openingTimer){
            mTimer.start();
        }
    }

    /**
     * 初始化恢复悬浮球翻转动画
     */
    private void initRecoverRotationAnimator() {
        mRotationAnimator = ObjectAnimator.ofFloat(mLogoView,"rotationX",0,LOGO_ROTATE_ANGLE);
        mRotationAnimator.setDuration(LOGO_ROTATE_TIME);
    }

    /**
     * 获取屏幕宽高和状态栏宽度
     */
    private void setScreenWH(){
        mScreenWidth = mDisplayMetrics.widthPixels;
        mScreenHeight = mDisplayMetrics.heightPixels;
        int id = mContext.getResources().getIdentifier("status_bar_height","dimen","android");
        if(id > 0){
            mStatusBarHeight = mContext.getResources().getDimensionPixelSize(id);
        }
    }

    public static final class Builder{
        private Bitmap mFloatMenuLogo;
        private Bitmap mFloatMenuOpenLogo;
        private Context mContext;
        private boolean isRotate = false;
        private boolean isTimer = false;
        private boolean mHalf = true;
        private int mRotate_time = 200;
        private List<Bitmap> mFloatMenuItems = new ArrayList<>();
        private FloatMenu.OnMenuClickListener mOnMenuClickListener;

        public Builder(){
        }

        public Builder setRotate(boolean rotate) {
            isRotate = rotate;
            return this;
        }

        public Builder setFloatMenuLogo(Bitmap FloatMenuLogo){
            this.mFloatMenuLogo = FloatMenuLogo;
            return this;
        }

        public Builder setFloatMenuOpenLogo(Bitmap FloatMenuOpenLogo){
            this.mFloatMenuOpenLogo = FloatMenuOpenLogo;
            return this;
        }

        public Builder setContext(Context context){
            this.mContext = context;
            return this;
        }

        public Builder setFloatMenuItem(List<Bitmap> list){
            this.mFloatMenuItems = list;
            return this;
        }

        public Builder setRotate_time(int time){
            this.mRotate_time = time;
            return this;
        }

        public Builder setTimer(boolean b){
            isTimer = b;
            return this;
        }

        public Builder setHalf(boolean b){
            mHalf = b;
            return this;
        }

        public FloatMenu setOnMenuClickListener(FloatMenu.OnMenuClickListener listener){
            mOnMenuClickListener = listener;
            return new FloatMenu(this);
        }
    }


}

