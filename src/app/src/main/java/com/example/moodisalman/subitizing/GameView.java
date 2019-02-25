package com.example.moodisalman.subitizing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;


public class GameView extends View  {
    private Bitmap obj;

    public GameView(Context context) {
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

        //Rect rect = new Rect(1/5* canvas.getWidth(), 2/5* canvas.getHeight(), (1/5 + canvas.getWidth()/5 ),(2/5 + canvas.getHeight()/5 ));

        Rect rect = new Rect(canvas.getWidth()/2+200, canvas.getHeight()/2+200, canvas.getWidth()/2+300,canvas.getHeight()/2+300);

        Rect rect1 = new Rect(canvas.getWidth()/2, canvas.getHeight()/2, canvas.getWidth()/2+100,canvas.getHeight()/2+100);
        //canvas.drawBitmap(obj, null, rect1, null);
        Rect rect2 = new Rect(canvas.getWidth()/2+150, canvas.getHeight()/2, canvas.getWidth()/2+250,canvas.getHeight()/2+100);

        Rect rect3 = new Rect(canvas.getWidth()/2, canvas.getHeight()/2+200, canvas.getWidth()/2+100,canvas.getHeight()/2+200);

        Rect rect4 = new Rect(canvas.getWidth()/2-200, canvas.getHeight()/2, canvas.getWidth()/2-100,canvas.getHeight()/2+100);

        Rect rect5 = new Rect(canvas.getWidth()/2, canvas.getHeight()/2+150, canvas.getWidth()/2+100,canvas.getHeight()/2+300);

        Rect rect6 = new Rect(canvas.getWidth()/2-200, canvas.getHeight()/2+200, canvas.getWidth()/2-100,canvas.getHeight()/2+300);

        if(level>=1)
            canvas.drawBitmap(obj, null, rect, null);
        if(level>=2)
            canvas.drawBitmap(obj, null, rect1, null);
        if(level>=3)
            canvas.drawBitmap(obj, null, rect2, null);
        if(level>=4)
            canvas.drawBitmap(obj, null, rect3, null);

        if(level>=5)
            canvas.drawBitmap(obj, null, rect4, null);

        if(level>=6)
            canvas.drawBitmap(obj, null, rect5, null);

        if(level>=7)
            canvas.drawBitmap(obj, null, rect6, null);



//        canvas.drawBitmap(obj, canvas.getWidth()/2, canvas.getHeight()/2, null);


//        for (int i=0;i<5;i++) {
//            for(int j=0;j<5;j++) {
//                int x = (i / 5) * canvas.getWidth();
//                int y = (j / 5) * canvas.getHeight() + 150;
//                Rect rect = new Rect(x, y, (x + canvas.getWidth()/5 ),(y + canvas.getHeight()/5 ));
//                canvas.drawBitmap(obj, null, rect, null);
//            }}

        // Animation loop (redraw this view by invalidate - call onDraw() - for animation loop
       invalidate();   // temporary method - will be replaced later by Animation Thread
    }





}
