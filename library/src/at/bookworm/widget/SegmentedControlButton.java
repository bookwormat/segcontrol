/*
 * Copyright 2011 Benjamin Ferrari
 * http://bookworm.at
 * https://github.com/bookwormat/segcontrol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.bookworm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.RadioButton;
import at.bookworm.R;

/**
 * @author benjamin ferrari
 * @author Grantland Chew <grantlandchew@gmail.com>
 */
public class SegmentedControlButton extends RadioButton {

    private CharSequence mTransformed;

    private boolean mTextAllCaps;

    private int mTextColorSelected;
    private int mTextColorUnselected;

    private int mLineColor;
    private int mLineHeightSelected;
    private int mLineHeightUnselected;

    private float mCenterX;
    private Rect mTextBounds = new Rect();

    private Drawable mBackgroundSelected;
    private Drawable mBackgroundUnselected;
    private Drawable mBackgroundPressed;
    private Paint mTextPaint;
    private Paint mLinePaint;

    public SegmentedControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public Drawable getBackgroundSelected() {
        return mBackgroundSelected;
    }

    public int getLineColor() {
        return mLineColor;
    }

    public int getLineHeightUnselected() {
        return mLineHeightUnselected;
    }

    public void init(AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            TypedArray attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedControlButton, defStyle, R.style.Widget_Holo_SegmentedControl);
            Drawable drawable;

            mBackgroundUnselected = this.getBackground();
            drawable = attributes.getDrawable(R.styleable.SegmentedControlButton_backgroundSelected);
            mBackgroundSelected = drawable != null ? drawable : getBackground();
            drawable = attributes.getDrawable(R.styleable.SegmentedControlButton_backgroundPressed);
            mBackgroundPressed = drawable != null ? drawable : getBackground();

            mTextAllCaps = attributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false);

            mTextColorUnselected = attributes.getColor(R.styleable.SegmentedControlButton_textColorUnselected, 0);
            mTextColorSelected = attributes.getColor(R.styleable.SegmentedControlButton_textColorSelected, 0);

            mLineColor = attributes.getColor(R.styleable.SegmentedControlButton_lineColor, 0);
            mLineHeightUnselected = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightUnselected, 0);
            mLineHeightSelected = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightSelected, 0);

            mTextPaint = new Paint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(getTextSize());
            mTextPaint.setTypeface(getTypeface());

            mLinePaint = new Paint();
            mLinePaint.setColor(this.getLineColor());
            mLinePaint.setStyle(Style.FILL);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        String text = mTransformed.toString();

        int lineHeight;
        if (isChecked()) {
            lineHeight = mLineHeightSelected;
            mTextPaint.setColor(mTextColorSelected);
        } else {
            lineHeight = mLineHeightUnselected;
            mTextPaint.setColor(mTextColorUnselected);
        }

        Drawable background = getBackground();
        if (background != null) {
            background.setBounds(0, 0, getWidth(), getHeight());
            background.draw(canvas);
        }

        mTextPaint.getTextBounds(text, 0, text.length(), mTextBounds);

        float textX = mCenterX;
        float textY = (getHeight() + mTextBounds.height()) / 2;

        canvas.drawText(text, textX, textY, mTextPaint);

        if (lineHeight > 0) {
            Rect rect = new Rect(0, this.getHeight() - lineHeight, getWidth(), this.getHeight());
            canvas.drawRect(rect, mLinePaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        mCenterX = w * 0.5f; // remember the center of the screen
    }

    @Override
    public void setChecked(boolean checked) {

        if (checked) {
            setBackgroundDrawable(mBackgroundSelected);
        } else {
            setBackgroundDrawable(mBackgroundUnselected);
        }

        super.setChecked(checked);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                setBackgroundDrawable(mBackgroundPressed);
            } break;
            case MotionEvent.ACTION_MOVE: {
                if (!inBounds(event.getX(), event.getY())) {
                    setBackgroundDrawable(mBackgroundUnselected);
                }
            } break;
            // We don't do ACTION_UP as the onCheckChanged deals with it
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE: {
                setBackgroundDrawable(mBackgroundUnselected);
            } break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_DPAD_CENTER == keyCode) {
            setBackgroundDrawable(mBackgroundPressed);
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_DPAD_CENTER == keyCode) {
            setBackgroundDrawable(mBackgroundUnselected);
        }

        return super.onKeyUp(keyCode, event);
    }

    // Used for android:textAllCaps
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (mTextAllCaps) {
            mTransformed = text.toString().toUpperCase();
        }
        else {
            TransformationMethod transformation = getTransformationMethod();
            if (transformation == null) {
                mTransformed = text;
            }
            else {
                mTransformed = transformation.getTransformation(text, this);
            }
        }
    }

    // Used for android:textStyle=bold
    @Override
    public void setTypeface(Typeface typeface) {
        if (mTextPaint != null) {
            mTextPaint.setTypeface(typeface);
        }
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
    }

    public void setTextColorSelected(int textColorSelected) {
        this.mTextColorSelected = textColorSelected;
    }

    public void setTextColorUnselected(int textColor) {
        this.mTextColorUnselected = textColor;
    }

    final boolean inBounds(float localX, float localY) {
        return localX >= 0 && localX < (getRight() - getLeft())
                && localY >= 0 && localY < (getBottom() - getTop());
    }
}
