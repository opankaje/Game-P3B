package com.example.tugasbesar2;


import android.util.Log;

public class GameThread implements Runnable{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean tanda;
    protected Player player;

    public GameThread (UIThreadedWrapper uiThreadedWrapper,Player player){
        this.uiThreadedWrapper=uiThreadedWrapper;
        this.thread=new Thread(this);
        this.player = player;
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
                        player.setX(10);
                        player.setY(0);
                        this.uiThreadedWrapper.setPlayer1(player);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Log.d("status tanda", tanda + " ");
                }
            }
    }


    public void stop(){
        thread.interrupt();
    }
}

