package com.project.me.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import static android.R.attr.gravity;

/**
 * Created by Sazumi on 19.07.2017.
 */

public class VerticalTextView extends android.support.v7.widget.AppCompatTextView {
   final boolean topDown;

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        final int gravity = getGravity();
        if (Gravity.isVertical(gravity)
                && (gravity& Gravity.VERTICAL_GRAVITY_MASK) == Gravity.BOTTOM) {
            setGravity((gravity& Gravity.HORIZONTAL_GRAVITY_MASK)
                    | Gravity.TOP);
            topDown = false;
        } else
            topDown = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        TextPaint textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());

        textPaint.drawableState = getDrawableState();

        canvas.save();

        if (topDown) {
            canvas.translate(0, getHeight());
            canvas.rotate(-90);
        } else {
            canvas.translate(getWidth(), 0);
            canvas.rotate(90);
        }

        canvas.translate(getCompoundPaddingLeft(), getExtendedPaddingTop());
        //canvas.drawColor(get);
        getLayout().draw(canvas);
        canvas.restore();
    }
    @Override
    public void draw(Canvas canvas) {

        //canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), mShadow);

        /*
            Draw the background setting by XML definition android:background
         */
        super.draw(canvas);

    }

}
