# FloatMenu


用法：
1、将aar文件导入到app/libs,在build.gradle文件下添加
repositories {
        flatDir {
            dirs 'libs'
        }
    }

    dependencies {
        compile(name:'floatmenu-debug', ext:'aar')
    }

2、在AndroidManifest.xml文件下,添加
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>权限
并在APP运行时申请悬浮窗权限，否则有可能打不开子菜单

3、应用程序下显示悬浮窗
FloatMenu mFloatMenu = new FloatMenu.Builder()
                            .setFloatMenuItem(itemList)
                            .setFloatMenuLogo(mLogoBitmap)
                            .setFloatMenuOpenLogo(openLogo)
                            .setContext(this)
                            .setRotate(true)
                            .setTimer(true)
			    .setHalf(true)
                            .setOnMenuClickListener(new FloatMenu.OnMenuClickListener() {
                                @Override
                                public void onClick(int position, View view) {
                                    Toast.makeText(context,"点击了 " + position,Toast.LENGTH_LONG).show();
                                }
                            });

--------------------------------------------------------------------

4、在桌面显示悬浮窗，需要新建服务类

public class FloatLogoMenuService extends Service {
    public LocalBinder localBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public FloatLogoMenuService getService() {
            return FloatLogoMenuService.this;
        }
--------------------------------------------------------------------
AndroidManifest.xml下添加

<application
        <activity android:name=".xxxxx"/>

        <service android:name=".FloatLogoMenuService">
        </service>

    </application>

------------------------------------------------------------

MainActivity.java

mServiceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    mLogoBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
                    Bitmap openLogo = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
                    itemList.add(bitmap);
                    itemList.add(bitmap);
                    itemList.add(bitmap);
                    itemList.add(bitmap);
                    itemList.add(bitmap);

                    FloatLogoMenuService.LocalBinder localBinder = (FloatLogoMenuService.LocalBinder) service;
                    mFloatLogoMenuService = localBinder.getService();

                    mFloatMenu = new FloatMenu.Builder()
                            .setFloatMenuItem(itemList)
                            .setFloatMenuLogo(mLogoBitmap)
                            .setFloatMenuOpenLogo(openLogo)
                            .setContext(mFloatLogoMenuService.getApplicationContext())
                            .setRotate(true)
                            .setTimer(true)
			    .setHalf(true)
                            .setOnMenuClickListener(new FloatMenu.OnMenuClickListener() {
                                @Override
                                public void onClick(int position, View view) {
                                    Toast.makeText(mFloatLogoMenuService.getApplicationContext(),"点击了 " + position,Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            };

            Intent intent = new Intent(MainActivity.this,FloatLogoMenuService.class);
            bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
        }
-------------------------------
参数：

.setFloatMenuItem(List<Bitmap>):子菜单的图标
.setFloatMenuLogo(Bitmap):普通状态下悬浮球的图标
.setFloatMenuOpenLogo(Bitmap):打开悬浮球状态的图标，不设置默认为普通状态图标
.setContext(Context):上下文
.setRotate(boolean): true:按下悬浮球旋转 false:不旋转
.setTimer(boolean):  true:定时隐藏悬浮球 false:不隐藏
.setHalf(boolean):   true:悬浮球半圆展开 false:矩形展开
.setOnMenuClickListener:设置悬浮球监听