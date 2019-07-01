package com.example.moodisalman.subitizing;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**This class is adapter of the slider that shown at the beginning of the game.**/

public class sliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public sliderAdapter(Context context) {

        this.context = context;
    }

    //arrays

    public int[] slideImages={
            R.drawable.welcome,
            R.drawable.login,
            R.drawable.guest

    };

    public String[] slideHeaders={
            "Welcome",
            "LogIn",
            "Guest"
    };

    public String[] slideDeses={
            "Subitizing game is about improving the subitizing skills, so if you need to improve" +
                    " your skills you need to start the subitizing game adventure right now.",
            "If you are with the research team, you will need to login by your userName ID for saving your results",
            "If you are a guest you can play in a guest mode, it's all the same games, so enjoy!"

    };

    @Override
    public int getCount() {
        return slideHeaders.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView iv=view.findViewById(R.id.imageView);
        TextView head=view.findViewById(R.id.textView);
        TextView des=view.findViewById(R.id.textView3);

        iv.setImageResource(slideImages[position]);
        head.setText(slideHeaders[position]);
        des.setText(slideDeses[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
