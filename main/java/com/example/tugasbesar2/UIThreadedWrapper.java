package com.example.tugasbesar2;

import android.os.Handler;
import android.os.Message;

public class UIThreadedWrapper extends Handler {
    protected final static int MSG_SET_PLAYER=0;
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
    }
    public void setPlayer(Player player){
        Message msg=new Message();
        msg.what=MSG_SET_PLAYER;
        msg.obj=player;
        this.sendMessage(msg);
    }
}
