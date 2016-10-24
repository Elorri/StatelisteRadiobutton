package com.example.android.statelisteradiobutton;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * Created by nebo-android2016 on 24/10/16.
 */
public class DrawableUtils {


    public static Drawable getStatelistDrawable( int[] states, Drawable[] drawables) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for(int i=0;i<states.length;i++) {
              stateListDrawable.addState(new int[]{states[i]}, drawables[i]);
        }
        return stateListDrawable;
    }

    public static Drawable addPaddingArround(int padding, Drawable drawable) {
        LayerDrawable withPadding=new LayerDrawable(new Drawable[]{drawable});
        withPadding.setLayerInset(0, padding, padding, padding, padding);
        return withPadding;
    }

    public static Drawable getLayerListDrawable(Drawable foregroundDrawable, Drawable backgroundDrawable) {
        Drawable layerDrawable=new LayerDrawable(new Drawable[]{backgroundDrawable, foregroundDrawable});
        return layerDrawable;
    }
}

