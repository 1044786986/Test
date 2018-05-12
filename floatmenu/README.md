# FloatMenu


�÷���
1����aar�ļ����뵽app/libs,��build.gradle�ļ������
repositories {
        flatDir {
            dirs 'libs'
        }
    }

    dependencies {
        compile(name:'floatmenu-debug', ext:'aar')
    }

2����AndroidManifest.xml�ļ���,���
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>Ȩ��
����APP����ʱ����������Ȩ�ޣ������п��ܴ򲻿��Ӳ˵�

3��Ӧ�ó�������ʾ������
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
                                    Toast.makeText(context,"����� " + position,Toast.LENGTH_LONG).show();
                                }
                            });

--------------------------------------------------------------------

4����������ʾ����������Ҫ�½�������

public class FloatLogoMenuService extends Service {
    public LocalBinder localBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public FloatLogoMenuService getService() {
            return FloatLogoMenuService.this;
        }
--------------------------------------------------------------------
AndroidManifest.xml�����

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
                                    Toast.makeText(mFloatLogoMenuService.getApplicationContext(),"����� " + position,Toast.LENGTH_SHORT).show();
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
������

.setFloatMenuItem(List<Bitmap>):�Ӳ˵���ͼ��
.setFloatMenuLogo(Bitmap):��ͨ״̬���������ͼ��
.setFloatMenuOpenLogo(Bitmap):��������״̬��ͼ�꣬������Ĭ��Ϊ��ͨ״̬ͼ��
.setContext(Context):������
.setRotate(boolean): true:������������ת false:����ת
.setTimer(boolean):  true:��ʱ���������� false:������
.setHalf(boolean):   true:�������Բչ�� false:����չ��
.setOnMenuClickListener:�������������