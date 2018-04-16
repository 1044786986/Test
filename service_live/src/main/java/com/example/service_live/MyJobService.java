package com.example.service_live;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

/**
 * Created by ljh on 2018/4/2.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService{
    @Override
    public void onCreate() {
        super.onCreate();
        startJobScheduler();
        Log.d("aaa","MyJobScheduler.onCreate()");
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void startJobScheduler(){
        JobInfo.Builder builder = new JobInfo.Builder(1,new ComponentName(getPackageName(),
                MyJobService.class.getName()));
                builder.setPeriodic(5);
                builder.setPersisted(true);
        JobScheduler jobScheduler = (JobScheduler) this.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}
