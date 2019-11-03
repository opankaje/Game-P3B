package com.example.tugasbesar2;

import android.util.Log;

public class BulletThread implements Runnable{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean tanda;
    protected Bullet bullet;

    public BulletThread (UIThreadedWrapper uiThreadedWrapper,Bullet bullet){
        this.uiThreadedWrapper=uiThreadedWrapper;
        this.thread=new Thread(this);
        this.bullet=bullet;
        tanda =true;
    }

    public void runThread(){
        this.thread.start();
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            if(tanda == true){
                try {
                    Thread.sleep(40);
                    bullet.setX(0);
                    bullet.setY(10);
                    this.uiThreadedWrapper.setBullet1(bullet);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                Log.d("status tanda", tanda + " ");
            }
        }
    }

    public void pause() {
        thread.interrupt();
    }

    public void startLagi(){
        thread.run();
    }

}
