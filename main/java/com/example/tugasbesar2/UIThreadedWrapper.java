package com.example.tugasbesar2;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

public class UIThreadedWrapper extends Handler {
    protected final static int MSG_SET_PLAYER=0;
    protected final static int MSG_SET_BULLET=1;
    protected final static int setBullets=2;

    protected FragmentGameplay fragmentGameplay;
    public UIThreadedWrapper(FragmentGameplay fragmentGameplay){
        this.fragmentGameplay=fragmentGameplay;
    }
    @Override
    public void handleMessage(Message msg){
        if(msg.what==UIThreadedWrapper.MSG_SET_PLAYER){
            Player player=(Player)msg.obj;
            this.fragmentGameplay.setPlayer(player);
        }
        if(msg.what==UIThreadedWrapper.MSG_SET_BULLET){
            Bullet bullet=(Bullet) msg.obj;
            this.fragmentGameplay.setPelor(bullet);
        }
        if (msg.what==UIThreadedWrapper.setBullets){
            ArrayList<Bullet> bullet=(ArrayList) msg.obj;
            this.fragmentGameplay.setArrBulList(bullet);
        }
    }
    public void setPlayer1(Player player){
        Message msg=new Message();
        msg.what=MSG_SET_PLAYER;
        msg.obj=player;
        this.sendMessage(msg);
    }
    public void setBullet1(Bullet bullet){
        Message msg=new Message();
        msg.what=MSG_SET_BULLET;
        msg.obj=bullet;
        this.sendMessage(msg);
    }

    public void setArrBullet(ArrayList bullets){
        Message msg=new Message();
        msg.what=setBullets;
        msg.obj=bullets;
        this.sendMessage(msg);
    }
}
