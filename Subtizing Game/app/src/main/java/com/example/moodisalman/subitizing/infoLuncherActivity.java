package com.example.moodisalman.subitizing;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**This activity for the info slider that shown at the beginning of the game.
 * **/

public class infoLuncherActivity extends AppCompatActivity {

    private ViewPager slideViewPager;
    private LinearLayout mDotLayout;
    private TextView[] mdots;
    private Button btnNext,btnSkip;
    private int pCurrent;
    private sliderAdapter sliderAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_luncher);

        slideViewPager=findViewById(R.id.viewPaager);
        mDotLayout=findViewById(R.id.layoutDot);
        btnNext=findViewById(R.id.btnNext);
        btnSkip=findViewById(R.id.btnSkip);

        sliderAdapter=new sliderAdapter(this);

        slideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pCurrent==mdots.length-1){
                    Intent i =new Intent(infoLuncherActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else
                    slideViewPager.setCurrentItem(pCurrent+1);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(infoLuncherActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    public void addDotsIndicator(int position){
        mdots=new TextView[3];
        mDotLayout.removeAllViews();

        for (int i=0 ; i<mdots.length;i++){
            mdots[i]=new TextView(this);

            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.white));

            mDotLayout.addView(mdots[i]);
        }

        if (mdots.length>0){
            mdots[position].setTextColor(getResources().getColor(R.color.onWhite));
        }
    }

    ViewPager.OnPageChangeListener  viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            pCurrent=position;

            if (position==mdots.length-1){
                btnSkip.setVisibility(View.INVISIBLE);
                btnNext.setText("Finish");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
