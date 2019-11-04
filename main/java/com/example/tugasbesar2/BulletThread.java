package com.example.tugasbesar2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.util.Log;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class BulletThread implements Runnable, CountScoreTask.Callback{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected ArrayList<Bullet> bullets;
    protected boolean tanda;
    protected Player player;
    protected int ukuranCanvas;
    protected int currScore;
    protected FragmentGameplay fg;

    public BulletThread (UIThreadedWrapper uiThreadedWrapper, ArrayList bullets,Player player,int ukuranCanvas, FragmentGameplay fg){
        this.uiThreadedWrapper=uiThreadedWrapper;
        this.thread=new Thread(this);
        this.player = player;
        this.currScore = 0;
        this.fg = fg;

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
                    if(this.bullets.get(i).getY()<100){
                        this.bullets.remove(i);
                        continue;
                    }
                    this.bullets.get(i).setY(this.bullets.get(i).getY() - 200);
                }
                this.uiThreadedWrapper.setArrBullet(this.bullets);

                try {
                    Thread.sleep(500);
                    Bullet bullet = new Bullet(player.getX()+220,1600);

                        //create background thread to connect and get data
                        String hasil = this.currScore + "+1";
                        this.currScore++;
                        try {
                            String query = URLEncoder.encode(hasil, "UTF-8");
                            Log.d("score", query + "");
                            new CountScoreTask(this).execute("http://api.mathjs.org/v4/?expr=" + query);
                        } catch (IOException e) {

                        }


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

    @Override
    public void sendResult(String result) {
        fg.changeScore(result);
    }
}
