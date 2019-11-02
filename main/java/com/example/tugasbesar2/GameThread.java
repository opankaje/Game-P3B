package com.example.tugasbesar2;

import java.util.Random;

public class GameThread implements Runnable{
    protected Thread thread;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean moveLeft;
    protected boolean moveRight;

    public GameThread (UIThreadedWrapper uiThreadedWrapper){
        this.uiThreadedWrapper=uiThreadedWrapper;
        this.thread=new Thread(this);
    }

    public void runThread(){
        this.thread.start();
    }

    @Override
    public void run(){
        for (int i=0;i<1000;i+=0){
            try {
                Thread.sleep(40);
                Player player = new Player(10,0);
                this.uiThreadedWrapper.setPlayer1(player);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
}

    public void pauseThread() {

    }
}
