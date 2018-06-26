package com.wangyuchao.a1500310106wyc_androidsy;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }
    private MyBinder myBinder = new MyBinder();

    class MyBinder extends Binder{
        public void text(){
            Log.d("MyService","bindService");
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }
}
