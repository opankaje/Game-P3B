package com.example.tugasbesar2;

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
        if(this.moveLeft == true) {

        }
        else if(this.moveRight == true) {
            Player player = new Player(10,0);
            this.uiThreadedWrapper.setPlayer(player);
        }
    }

    public void pauseThread() {

    }
}
