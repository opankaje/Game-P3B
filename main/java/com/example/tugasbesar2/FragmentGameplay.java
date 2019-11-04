package com.example.tugasbesar2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
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

import java.util.ArrayList;

public class FragmentGameplay extends Fragment implements View.OnClickListener{
    protected TextView tvLivesRem;
    protected TextView tvStart;
    protected TextView tvScore;
    protected ImageButton ibPause;
    protected ImageView ivGameScreen;
    protected Bitmap bitmap;
    protected Canvas canvas;
    protected Player bos;
    protected Paint paint;
    protected UIThreadedWrapper uiThreadedWrapper;
    protected boolean initiated;
    protected GameThread movePlayerThread;
    protected Player player;
    public int midXCanvas;
    protected int x;
    protected int y;
    protected int yPeluru;
    protected int xBos;
    protected int yBos;
    protected boolean gerak;
    protected BulletThread bulletThread;
    ArrayList<Bullet> bullets;
    protected int nyawa;

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
        this.tvScore = view.findViewById(R.id.tv_score);
        this.ivGameScreen = view.findViewById(R.id.ivGame);
        this.ibPause.setOnClickListener(this);
        this.uiThreadedWrapper=new UIThreadedWrapper(this);
        this.midXCanvas = 0;
        this.bullets = new ArrayList<>();

        this.tvStart = view.findViewById(R.id.tv_start);
        this.tvStart.setOnClickListener(this);
        this.gerak = false;
        this.initiated = true;
        this.nyawa=3;
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

        //initiate paint
        this.paint = new Paint();
        int warna = ResourcesCompat.getColor(getResources(),R.color.white,null);
        this.paint.setColor(warna);

        this.x =(ivGameScreen.getWidth()/2-150);
        this.y =canvas.getHeight()-300;
        this.xBos =(canvas.getWidth()/2)-150;
        Log.d("debug", "posisi x: " + canvas.getWidth());
        Log.d("debug", "posisi y: " + y);

        this.player = new Player(x,y);
        this.bos = new Player(this.xBos,10);
        this.movePlayerThread = new GameThread(this.uiThreadedWrapper,bos);
        this.bulletThread = new BulletThread(uiThreadedWrapper,bullets,bos,canvas.getHeight(), this);

        //reset canvas
        resetCanvas();

        Log.d("debug", "bitmap: " + bitmap.getWidth());
        Log.d("debug", "bitmap: " + bitmap.getHeight());


        gambarPlayer(x,y);
        gambarBos(xBos,10);
        runThread();

    }

    public void runThread(){
        this.movePlayerThread.runThread();
        this.bulletThread.runThread();
    }

    public void resetCanvas(){
        //create game background
        int colorBackground=ResourcesCompat.getColor(getResources(),R.color.hitam,null);
//        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawColor(colorBackground);
        //force draw
        this.ivGameScreen.invalidate();
    }

    public void gambarPeluru(int x,int y){
        this.canvas.drawCircle(x,y,20,paint);
    }

    public void gambarBos(int x,int y){
        Bitmap bos;
        bos = BitmapFactory.decodeResource(getResources(),R.drawable.bos);

        canvas.drawBitmap(bos,x,y,paint);
    }

    public void gambarPlayer(int x, int y){
        Bitmap player;
        player = BitmapFactory.decodeResource(getResources(),R.drawable.player);
        this.canvas.drawBitmap(player,x,y,paint);
//        Player player1 = new Player(xBos,-10);
//        this.bulletThread.setPlayer(player1);
//        for (int i = 0 ; i < this.bullets.size();i++){
//            gambarPeluru(this.bullets.get(i).getX(),this.bullets.get(i).getY());
//        }
    }

    public void setPlayer(Player player){

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

        this.bos=new Player(xBos,yBos);
//            if(this.bullets.get(i).getX()>=player.getX()&&this.bullets.get(i).getX()<=player.getY()+170){
//                this.tvScore.setText("0");
//            }
    }

    public void gamePlay(){
        resetCanvas();
        MainActivity mainActivity=(MainActivity)getActivity();
        gambarBos(xBos,yBos);
        gambarPlayer(x+mainActivity.gerakKiriKanan(),y);
        Player player1=new Player(x+mainActivity.gerakKiriKanan(),1200);
        this.bulletThread.setPlayer(player1);
        for(int i=0;i<this.bullets.size();i++){
            gambarPeluru(this.bullets.get(i).getX(),this.bullets.get(i).getY());
            if(this.bullets.get(i).getX()>=this.bos.getX()&&this.bullets.get(i).getX()<=this.bos.getX()+170&&
                    this.bullets.get(i).getY()==this.bos.getY()){
                nyawa--;
                this.bullets.remove(i);
                if(nyawa>=0) {
                    this.tvLivesRem.setText(nyawa + "");
                }else{
                    this.tvLivesRem.setText("0");
                }
            }
        }

        Log.d("Nilai x bos",""+this.bos.getX());
    }

    public void setArrBulList(ArrayList<Bullet> bulList){
        this.bullets = bulList;
    }

    public void setPelor(Bullet bullet){
        this.bullets.add(bullet);
        for(int i=0;i<this.bullets.size();i++){
            gambarPeluru(this.bullets.get(i).getX(),this.bullets.get(i).getY());
        }
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
                this.bulletThread.tanda = false;
                Log.d("test","play");
            }else {
                ibPause.setImageResource(android.R.drawable.ic_media_pause);
                initiated = false;
                movePlayerThread.tanda =true;
                this.bulletThread.tanda =true;
                Log.d("test","pause");

            }

        }
    }

    public void changeScore(String score) {
        this.tvScore.setText("Score : " + score);
    }
}

