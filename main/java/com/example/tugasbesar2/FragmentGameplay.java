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
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean initiated;
    protected GameThread movePlayerThread;
    protected Player player;
    public int midXCanvas;
    protected int x;
    protected int y;

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

        this.x =(canvas.getWidth()/2)-220;
        this.y =canvas.getHeight()-420;
        int x1 =(canvas.getWidth()/2)-150;
        Log.d("debug", "posisi x: " + canvas.getWidth());
        Log.d("debug", "posisi y: " + canvas.getHeight());

        this.player = new Player(x,y);
        this.movePlayerThread = new GameThread(this.uiThreadedWrapper);

        //reset canvas
        this.midXCanvas = this.canvas.getWidth()/2;
        resetCanvas();

        this.bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.player);


        Log.d("debug", "bitmap: " + bitmap.getWidth());
        Log.d("debug", "bitmap: " + bitmap.getHeight());


        Bitmap bitmap1= Bitmap.createBitmap(ivGameScreen.getWidth(),ivGameScreen.getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmap1=BitmapFactory.decodeResource(getResources(),R.drawable.bos);
        Paint paint = new Paint();
        canvas.drawBitmap(bitmap,x,y,paint);
        canvas.drawBitmap(bitmap1,x1,10,paint);
        //runThread();

    }

    public void runThread(){
        GameThread thread = new GameThread(this.uiThreadedWrapper);
        thread.runThread();
    }

    public void resetCanvas(){
        //create game background
        int background = ResourcesCompat.getColor(getResources(),
                R.color.hitam,null);
        canvas.drawColor(background);

//        Paint paint = new Paint();
//        int mColorTest=ResourcesCompat.getColor(getResources(),R.color.hitam,null);
//        paint.setColor(mColorTest);
//        canvas.drawCircle(x, y, 150, paint);


        //force draw
        this.ivGameScreen.invalidate();

//        Point a= new Point(canvas.getWidth()/2,canvas.getHeight()-150);
//        Point b= new Point(125, 275);
//        Point c= new Point(275, 275);
//        Path path= new Path();
//        path.moveTo(a.x, a.y);
//        path.lineTo(b.x,b.y);
//        path.lineTo(c.x,c.y);
//        path.lineTo(a.x,a.y);
//        canvas.drawPath(path,paint);

    }

    public void setPlayer(Player player){
        resetCanvas();

        //ambil nilai x dan y dari thread
        this.x += player.getX();

        //menggerakan lingkaran
        Paint paint = new Paint();
        int mColorTest=ResourcesCompat.getColor(getResources(),R.color.hitam,null);
        paint.setColor(mColorTest);
        //canvas.drawCircle(x, y, 150, paint);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == this.tvStart.getId()) {
            this.tvStart.setText("");
            this.setInitiatedCanvas();
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getX() <= this.midXCanvas) {
                    this.movePlayerThread.moveLeft = true;
                    this.movePlayerThread.moveRight = false;
                    runThread();
                }
                else if(motionEvent.getX() > this.midXCanvas) {
                    this.movePlayerThread.moveRight = true;
                    this.movePlayerThread.moveLeft = false;
                    runThread();
                }
                Log.d("touch_listener", "down");
                break;
            case MotionEvent.ACTION_UP:
                //runThread();
                this.movePlayerThread.moveLeft = false;
                this.movePlayerThread.moveRight = false;
                Log.d("touch_listener", "up");
                break;
            case MotionEvent.ACTION_MOVE:
                if(motionEvent.getX() >= this.midXCanvas) {
                    //runThread();
                }
                Log.d("touch_listener", "move");
                break;
        }
        return true;
    }
}
