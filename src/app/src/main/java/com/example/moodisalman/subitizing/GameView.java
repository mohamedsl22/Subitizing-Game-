package com.example.moodisalman.subitizing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import java.util.Vector;


public class GameView extends View  {
    private Bitmap obj;
    private Rect rect[];

    public GameView(Context context) {//constructor
        super(context);

        obj= BitmapFactory.decodeResource(getResources(),R.drawable.fish);

    }

    // states
    private enum State {GET_READY, PLAYING, GAME_OVER};

    // objects

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int level=video.level;

        if(level==1){
            Rect rect = new Rect(canvas.getWidth()/3, canvas.getHeight()/3, 2*(canvas.getWidth()/3),2*(canvas.getHeight()/3));
            canvas.drawBitmap(obj, null, rect, null);
        }
        if (level==2){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==3){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==4){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==5){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==6){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==7){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==8){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==9){
            for (int i=0;i<level;i++)
                canvas.drawBitmap(obj, null, drawRects(level,canvas)[i], null);
        }
        if (level==-1){

        }






        // Animation loop (redraw this view by invalidate - call onDraw() - for animation loop
       invalidate();   // temporary method - will be replaced later by Animation Thread
    }


    protected Rect[] drawRects(int lev ,Canvas canvas){//places of objects according to levels num

        rect=new Rect[9];//9=max num of objects that can be
        if (lev==2) {
            rect[0]=new Rect(canvas.getWidth()/4, canvas.getHeight()/3, 2*(canvas.getWidth()/4),2*(canvas.getHeight()/3));
            rect[1]=new Rect(2*(canvas.getWidth()/4), canvas.getHeight()/3, 3*(canvas.getWidth()/4),2*(canvas.getHeight()/3));
        }
        if (lev==3){
            rect[0]=new Rect(canvas.getWidth()/4, canvas.getHeight()/4, 2*(canvas.getWidth()/4),2*(canvas.getHeight()/4));
            rect[1]=new Rect(canvas.getWidth()/4, 2*(canvas.getHeight()/4), 2*(canvas.getWidth()/4),3*(canvas.getHeight()/4));
            rect[2]=new Rect(2*(canvas.getWidth()/4), 2*(canvas.getHeight()/4), 3*(canvas.getWidth()/4),3*(canvas.getHeight()/4));
        }
        if (lev==4){
            rect[0]=new Rect(canvas.getWidth()/4, canvas.getHeight()/4, 2*(canvas.getWidth()/4),2*(canvas.getHeight()/4));
            rect[1]=new Rect(2*(canvas.getWidth()/4),canvas.getHeight()/4,3*(canvas.getWidth()/4),2*(canvas.getHeight()/4));
            rect[2]=new Rect(canvas.getWidth()/4, 2*(canvas.getHeight()/4), 2*(canvas.getWidth()/4),3*(canvas.getHeight()/4));
            rect[3]=new Rect(2*(canvas.getWidth()/4), 2*(canvas.getHeight()/4), 3*(canvas.getWidth()/4),3*(canvas.getHeight()/4));
        }
        if (lev==5){
            rect[0]=new Rect(30, 100, canvas.getWidth() / 3, canvas.getHeight() / 3 );
            rect[1]=new Rect(2*(canvas.getWidth() / 3), 100, canvas.getWidth()-30, canvas.getHeight() / 3);
            rect[2]=new Rect(canvas.getWidth()/3, canvas.getHeight()/3, 2*(canvas.getWidth() / 3)+50,2*(canvas.getHeight()/3));
            rect[3]=new Rect(30, 2*(canvas.getHeight()/3) , canvas.getWidth() / 3, canvas.getHeight() -100);
            rect[4]=new Rect(2*(canvas.getWidth() / 3), 2*(canvas.getHeight()/3), canvas.getWidth() -30, canvas.getHeight()-100);
        }
        if (lev==6) {
            rect[0]=new Rect(canvas.getWidth()/4, 90, 2*(canvas.getWidth()/4),1*(canvas.getHeight()/4)+90);
            rect[1]=new Rect(2*(canvas.getWidth()/4),90,3*(canvas.getWidth()/4),1*(canvas.getHeight()/4)+90);
            rect[2]=new Rect(canvas.getWidth()/4, 1*(canvas.getHeight()/4)+90, 2*(canvas.getWidth()/4),2*(canvas.getHeight()/4)+90);
            rect[3]=new Rect(2*(canvas.getWidth()/4), 1*(canvas.getHeight()/4)+90, 3*(canvas.getWidth()/4),2*(canvas.getHeight()/4)+90);
            rect[4]=new Rect(canvas.getWidth()/4, 2*(canvas.getHeight()/4)+90, 2*(canvas.getWidth()/4),3*(canvas.getHeight()/4)+90);
            rect[5]=new Rect(2*(canvas.getWidth()/4), 2*(canvas.getHeight()/4)+90, 3*(canvas.getWidth()/4),3*(canvas.getHeight()/4)+90);

        }
        if (lev==7) {
            rect[0]=new Rect(30, (canvas.getHeight() / 4), canvas.getWidth() / 5-10, 2*(canvas.getHeight() / 4));
            rect[1]=new Rect(canvas.getWidth() / 5 + 10, (canvas.getHeight() / 4), 2*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4));
            rect[2]=new Rect(3*(canvas.getWidth() / 5), (canvas.getHeight() / 4), 4*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4));
            rect[3]=new Rect(30, 2*(canvas.getHeight() / 4), canvas.getWidth() / 5-10, 3*(canvas.getHeight() / 4));
            rect[4]=new Rect(canvas.getWidth() / 5 + 10, 2*(canvas.getHeight() / 4)+10, 2*(canvas.getWidth() / 5), 3*(canvas.getHeight() / 4));
            rect[5]=new Rect(3*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4)+10, 4*(canvas.getWidth() / 5), 3*(canvas.getHeight() / 4) );
            rect[6]=new Rect(4*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4)+10, 5*(canvas.getWidth() / 5), 3*(canvas.getHeight() / 4) );
        }
        if (lev==8) {
            rect[0]=new Rect(30, (canvas.getHeight() / 4), canvas.getWidth() / 5-10, 2*(canvas.getHeight() / 4));
            rect[1]=new Rect(canvas.getWidth() / 5 + 10, (canvas.getHeight() / 4), 2*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4));
            rect[2]=new Rect(3*(canvas.getWidth() / 5), (canvas.getHeight() / 4), 4*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4));
            rect[3]=new Rect(4*(canvas.getWidth() / 5), (canvas.getHeight() / 4), 5*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4));
            rect[4]=new Rect(30, 2*(canvas.getHeight() / 4), canvas.getWidth() / 5-10, 3*(canvas.getHeight() / 4));
            rect[5]=new Rect(canvas.getWidth() / 5 + 10, 2*(canvas.getHeight() / 4), 2*(canvas.getWidth() / 5), 3*(canvas.getHeight() / 4));
            rect[6]=new Rect(3*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4), 4*(canvas.getWidth() / 5), 3*(canvas.getHeight() / 4) );
            rect[7]=new Rect(4*(canvas.getWidth() / 5), 2*(canvas.getHeight() / 4), 5*(canvas.getWidth() / 5), 3*(canvas.getHeight() / 4) );
        }
        if (lev==9) {
            rect[0]=new Rect(30, (canvas.getHeight() / 5), canvas.getWidth() / 6, 2*(canvas.getHeight() / 5));
            rect[1]=new Rect(2*(canvas.getWidth() / 6 ), (canvas.getHeight() / 5), 3*(canvas.getWidth() / 6), 2*(canvas.getHeight() / 5));
            rect[2]=new Rect(4*(canvas.getWidth() / 6), (canvas.getHeight() / 5), 5*(canvas.getWidth() / 6), 2*(canvas.getHeight() / 5));
            rect[3]=new Rect(5*(canvas.getWidth() / 6), (canvas.getHeight() / 5), 6*(canvas.getWidth() / 6), 2*(canvas.getHeight() / 5));
            rect[4]=new Rect(canvas.getWidth()/6,2*(canvas.getHeight() / 5),2*(canvas.getWidth()/6),3*(canvas.getHeight() / 5));
            rect[5]=new Rect(30, 3*(canvas.getHeight() / 5), canvas.getWidth() / 6, 4*(canvas.getHeight() / 5));
            rect[6]=new Rect(2*(canvas.getWidth() / 6 ), 3*(canvas.getHeight() /5), 3*(canvas.getWidth() / 6), 4*(canvas.getHeight() / 5));
            rect[7]=new Rect(4*(canvas.getWidth() / 6), 3*(canvas.getHeight() / 5), 5*(canvas.getWidth() / 6), 4*(canvas.getHeight() / 5) );
            rect[8]=new Rect(5*(canvas.getWidth() / 6), 3*(canvas.getHeight() / 5), 6*(canvas.getWidth() / 6), 4*(canvas.getHeight() / 5) );
        }

        return rect;
    }






}
