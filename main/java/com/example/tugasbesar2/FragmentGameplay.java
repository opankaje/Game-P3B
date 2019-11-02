package com.example.tugasbesar2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

public class FragmentGameplay extends Fragment implements View.OnClickListener, View.OnTouchListener {
    protected TextView tvLivesRem;
    protected TextView tvStart;
    protected ImageButton ibPause;
    protected ImageView ivGameScreen;
    protected Bitmap bitmap;
    protected Canvas canvas;
    protected Player bos;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean initiated;
    protected GameThread movePlayerThread;
    protected Player player;
    public int midXCanvas;
    protected int x;
    protected int y;
    protected int xBos;
    protected int yBos;
    protected int yPeluru;
    protected boolean gerak;

    public FragmentGameplay() {
        //empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gameplay, container, false);
        //atribut initiated
        this.tvLivesRem = view.findViewById(R.id.tv_lives_remaining);
        this.ibPause = view.findViewById(R.id.btnpause);
        this.ivGameScreen = view.findViewById(R.id.ivGame);
        this.ibPause.setOnClickListener(this);
        this.uiThreadedWrapper=new UIThreadedWrapper(this);
        this.ivGameScreen.setOnTouchListener(this);
        this.midXCanvas = 0;

        this.tvStart = view.findViewById(R.id.tv_start);
        this.tvStart.setOnClickListener(this);
        this.gerak = false;
        this.initiated = true;
        return view;
    }


    public void setInitiatedCanvas(){
        //create bitmap
        this.bitmap = Bitmap.createBitmap(ivGameScreen.getWidth(),ivGameScreen.getHeight(),
                Bitmap.Config.ARGB_8888);


        //associate bitmap with image view
        this.ivGameScreen.setImageBitmap(bitmap);

        //create canvas
        this.canvas = new Canvas(bitmap);

        this.yPeluru=60;
        this.x =(ivGameScreen.getWidth()/2-150);
        this.y =canvas.getHeight()-300;
        this.xBos =(canvas.getWidth()/2)-150;
        Log.d("debug", "posisi x: " + canvas.getWidth());
        Log.d("debug", "posisi y: " + canvas.getHeight());

        this.player = new Player(x,y);
        this.bos = new Player(this.xBos,10);
        this.movePlayerThread = new GameThread(this.uiThreadedWrapper,bos);

        //reset canvas
        this.midXCanvas = this.canvas.getWidth()/2;
        resetCanvas();

        this.bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player);


        Log.d("debug", "bitmap: " + bitmap.getWidth());
        Log.d("debug", "bitmap: " + bitmap.getHeight());


        Bitmap bitmap1;
        bitmap1=BitmapFactory.decodeResource(getResources(),R.drawable.bos);
        Paint paint = new Paint();

        canvas.drawBitmap(bitmap,x,y,paint);
        canvas.drawBitmap(bitmap1,xBos,10,paint);
        runThread();

    }

    public void runThread(){
        this.movePlayerThread.runThread();
    }

    public void resetCanvas(){
        //create game background
        int background = ResourcesCompat.getColor(getResources(),
                R.color.hitam,null);
        canvas.drawColor(background);

        //force draw
        this.ivGameScreen.invalidate();
    }

    public void setPlayer(Player player){

        //ambil nilai x dan y dari thread
//        int x = xBos;
        //menggerakan bos
        if(gerak == false && this.xBos+170 < ivGameScreen.getWidth()){
            this.xBos+= player.getX();
        }else
        {
            gerak = true;
        }

        if(gerak == true && this.xBos > 0 ){
            this.xBos-=player.getX();
        }
        else{
            gerak=false;
        }

        resetCanvas();

        Bitmap bitmap1;
        bitmap1=BitmapFactory.decodeResource(getResources(),R.drawable.bos);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap1,xBos,yBos,paint);

        this.bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player);
        canvas.drawBitmap(this.bitmap,this.x,this.y,paint);
    }

    public void setPeluru(int y){
        yPeluru += y;
        Paint paint1 = new Paint();
        int warnPel = ResourcesCompat.getColor(getResources(),R.color.white,null);
        paint1.setColor(warnPel);
        canvas.drawCircle(xBos,yPeluru+player.getX(),20,paint1);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == this.tvStart.getId()) {
            this.tvStart.setText("");
            initiated = false;
            ibPause.setImageResource(android.R.drawable.ic_media_pause);
            this.setInitiatedCanvas();
        }
        if(view.getId() == this.ibPause.getId()){
            if(initiated ==false){
                ibPause.setImageResource(android.R.drawable.ic_media_play);
                initiated = true;
                this.movePlayerThread.tanda =false;
                Log.d("test","play");
            }else {
                ibPause.setImageResource(android.R.drawable.ic_media_pause);
                initiated = false;
                movePlayerThread.tanda =true;
                Log.d("test","pause");

            }

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getX() <= this.midXCanvas) {
//                    this.movePlayerThread.moveLeft = true;
//                    this.movePlayerThread.moveRight = false;
                }
                else if(motionEvent.getX() > this.midXCanvas) {
//                    this.movePlayerThread.moveRight = true;
//                    this.movePlayerThread.moveLeft = false;
                }
                Log.d("touch_listener", "down");
                break;
            case MotionEvent.ACTION_UP:
//                this.movePlayerThread.moveLeft = false;
//                this.movePlayerThread.moveRight = false;
                Log.d("touch_listener", "up");
                break;
            case MotionEvent.ACTION_MOVE:
                if(motionEvent.getX() >= this.midXCanvas) {
                }
                Log.d("touch_listener", "move");
                break;
        }
        return true;
    }
}
