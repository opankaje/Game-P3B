package com.example.tugasbesar2;

import android.os.Handler;
import android.os.Message;

public class UIThreadedWrapper extends Handler {
    protected final static int MSG_SET_PLAYER=0;
    protected final static int MSG_SET_BULLET=1;
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
            Bullet bullet=(Bullet)msg.obj;
            this.fragmentGameplay.setPeluru(bullet.getX(), bullet.getY());
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
}
