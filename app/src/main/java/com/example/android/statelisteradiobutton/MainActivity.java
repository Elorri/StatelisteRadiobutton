package com.example.android.statelisteradiobutton;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View radiobutton = findViewById(R.id.radiobutton);

        Drawable backgroundDrawable = getResources().getDrawable(R.drawable.circle_background);
        backgroundDrawable.setTint(Color.BLUE);

        Drawable foregroundDrawable = getResources().getDrawable(R.drawable.ic_notebook);
        foregroundDrawable.setTint(Color.WHITE);
        DrawableUtils.addPaddingArround((int) getResources().getDimension(R.dimen.padding), foregroundDrawable);


        Drawable checkedDrawable = DrawableUtils.getLayerListDrawable(foregroundDrawable, backgroundDrawable);

        Drawable uncheckedDrawable = getResources().getDrawable(R.drawable.ic_notebook);
        uncheckedDrawable.setTint(Color.RED);
        DrawableUtils.addPaddingArround((int) getResources().getDimension(R.dimen.padding), uncheckedDrawable);


        int[] states = new int[]{-android.R.attr.state_checked, android.R.attr.state_checked};
        Drawable[] drawables = new Drawable[]{uncheckedDrawable, checkedDrawable};

        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "");
        radiobutton.setBackground(DrawableUtils.getStatelistDrawable(states, drawables));
        radiobutton.requestLayout();
    }
}
