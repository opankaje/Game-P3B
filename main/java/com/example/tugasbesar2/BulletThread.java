package com.example.tugasbesar2;

import android.util.Log;

import java.util.ArrayList;

public class BulletThread implements Runnable{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected ArrayList<Bullet> bullets;
    protected boolean tanda;
    protected Player player;
    protected int ukuranCanvas;

    public BulletThread (UIThreadedWrapper uiThreadedWrapper, ArrayList bullets,Player player,int ukuranCanvas){
        this.uiThreadedWrapper=uiThreadedWrapper;
        this.thread=new Thread(this);
        this.player = player;

        this.bullets = bullets;
        this.ukuranCanvas = ukuranCanvas;
        tanda =true;
    }

    public void runThread(){
        this.thread.start();
    }

    @Override
    public void run() {
        while (!thread.isInterrupted()) {
            if(tanda == true){

                for (int i = 0; i < this.bullets.size(); i++) {
                    if(this.bullets.get(i).getY()>ukuranCanvas){
                        this.bullets.remove(i);
                        continue;
                    }
                    this.bullets.get(i).setY(this.bullets.get(i).getY() - 500);
                }
                this.uiThreadedWrapper.setArrBullet(this.bullets);

                try {
                    Thread.sleep(500);
                    Bullet bullet = new Bullet(player.getX()+220,1600);
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }

    public void startLagi(){
        thread.run();
    }

}
