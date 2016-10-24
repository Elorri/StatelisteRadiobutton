package com.example.android.statelisteradiobutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RadioButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class StatelistRadiobutton extends RadioButton {
    private Drawable backgroundDrawable;
    private Drawable checkedDrawable;
    private Drawable uncheckedDrawable;

    private int imageWidth;
    private int imageHeight;
    private int paddingTop;
    private int paddingBottom;
    private int paddingRight;
    private int paddingLeft;

    public StatelistRadiobutton(Context context, AttributeSet attrs) {
        super(context, attrs);

        //read the value of android:background and save it in a member variable
        if (attrs != null) {
            int[] attrsArray = {
                    android.R.attr.columnWidth
            };
            TypedArray array = context.obtainStyledAttributes(attrs, attrsArray);
            backgroundDrawable = array.getDrawable(0);
            array.recycle();

            if(backgroundDrawable instanceof StateListDrawable) {
                try {
                    Method getStateDrawableIndex = StateListDrawable.class.getMethod("getStateDrawableIndex", int[].class);
                    Method getStateDrawable = StateListDrawable.class.getMethod("getStateDrawable", int.class);
                    //int index = (int) getStateDrawableIndex.invoke(stateListDrawable,currentState);
                    checkedDrawable = (Drawable) getStateDrawable.invoke(backgroundDrawable, 0);
                    uncheckedDrawable = (Drawable) getStateDrawable.invoke(backgroundDrawable, 1);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "");
        if (backgroundDrawable == null) {
            Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "getMeasuredWidth() " + getMeasuredWidth() + " getMeasuredHeight() " + getMeasuredHeight());
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "getMeasuredWidth() " + getMeasuredWidth() + " getMeasuredHeight() " + getMeasuredHeight());
            return;
        }
        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "getMeasuredWidth() " + getMeasuredWidth() + " getMeasuredHeight() " + getMeasuredHeight());

        int drawableHeight = backgroundDrawable.getIntrinsicHeight();
        int drawableWidth = backgroundDrawable.getIntrinsicWidth();

        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "backgroundDrawable.getIntrinsicWidth() " + backgroundDrawable.getIntrinsicWidth() + " backgroundDrawable.getIntrinsicHeight() " + backgroundDrawable.getIntrinsicHeight());

        //Get the padding if the user has set one
        paddingTop = getPaddingTop();
        paddingBottom = getPaddingBottom();
        paddingRight = getPaddingRight();
        paddingLeft = getPaddingLeft();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec); //we call this to have values for getMeasuredWidth and getMeasuredHeight
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        //Set the size of the image we want to draw to be the size of the drawable (getIntrinsicWidth -> if wrap content is selected) or the size of xml layout_width and layout _height (getMeasuredWidth -> if match_parent or size is given)
        imageWidth = layoutParams.width == ViewGroup.LayoutParams.WRAP_CONTENT ? drawableWidth : getMeasuredWidth() - paddingRight - paddingLeft;
        imageHeight = layoutParams.height == ViewGroup.LayoutParams.WRAP_CONTENT ? drawableHeight : getMeasuredHeight() - paddingRight - paddingLeft;

        Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "imageWidth " + imageWidth + " imageHeight " + imageHeight);

        //We set the size of the canvas we plan to draw on.
        // Here we set the exact size of the drawable plus the eventual padding
        setMeasuredDimension(imageWidth + paddingRight + paddingLeft, imageHeight + paddingTop + paddingBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (backgroundDrawable == null) {
            Log.e("Nebo", Thread.currentThread().getStackTrace()[2] + "");
            return;
        }
        Drawable drawable = isChecked() ? checkedDrawable : uncheckedDrawable;
//        //Drawable drawable = backgroundDrawable;
//        drawable.setTint(Color.RED);

        if(isChecked()){
            checkedDrawable.setTint(Color.WHITE);
        }else{
            uncheckedDrawable.setTint(Color.BLUE);
        }

        //We set the canvas to be the exact size of our drawable. Our drawable can now draw itself
        // on it and be completely visible.
        int left = paddingLeft;
        int top = paddingTop;
        int right = paddingLeft + imageWidth;
        int bottom = paddingTop + imageHeight;
        drawable.setBounds(left, top, right, bottom);
        drawable.draw(canvas);
    }
}