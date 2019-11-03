package com.example.tugasbesar2;

import android.util.Log;

public class MovePlayerThread implements Runnable{
    protected Thread thread;
    protected PlayerThreadHandler handler;
    protected boolean tanda;
    protected boolean moveLeft;
    protected boolean moveRight;
    protected Player player;

    public MovePlayerThread (PlayerThreadHandler handler,Player player){
        this.handler = handler;
        this.thread=new Thread(this);
        this.player = player;
        tanda =true;
        this.moveLeft = false;
        this.moveRight = false;
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
                    if(this.moveRight == true) {
                        player.setX(10);
                        player.setY(0);
                        this.handler.setPlayer(player);
                    }
                    if(this.moveLeft == true) {
                        player.setX(-10);
                        player.setY(0);
                        this.handler.setPlayer(player);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
