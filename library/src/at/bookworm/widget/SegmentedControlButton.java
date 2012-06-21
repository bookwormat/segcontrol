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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import at.bookworm.R;

/**
 * @author benjamin ferrari
 * @author Grantland Chew <grantlandchew@gmail.com>
 */
public class SegmentedControlButton extends RadioButton {

    private Drawable mBackgroundSelected;
    
    private boolean mAllCaps;
    private int mTextColorSelected;
    private int mTextColorUnselected;

    private int mLineColor;
    private int mLineHeightSelected;
    private int mLineHeightUnselected;

    private float mCenterX;
    private Rect mTextBounds = new Rect();

    private Drawable mBackgroundUnselected;
    private Paint mTextPaint;
    private Paint mLinePaint;

    public SegmentedControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
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

    public void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedControlButton);
            if (mBackgroundSelected == null) {
                Drawable d = attributes.getDrawable(R.styleable.SegmentedControlButton_backgroundSelected);
                mBackgroundSelected = d == null ? getBackground() : d;
            }

            if (mBackgroundUnselected == null) {
                mBackgroundUnselected = this.getBackground();
            }

            mAllCaps = attributes.getBoolean(R.styleable.TextAppearance_allCaps, false);
            mTextColorUnselected = attributes.getColor(R.styleable.SegmentedControlButton_textColorUnselected, 0);
            mTextColorSelected = attributes.getColor(R.styleable.SegmentedControlButton_textColorSelected, 0);

            mLineColor = attributes.getColor(R.styleable.SegmentedControlButton_lineColor, 0);
            mLineHeightUnselected = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightUnselected, 0);
            mLineHeightSelected = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightSelected, 0);

            mTextPaint = new Paint();
            mTextPaint.setAntiAlias(true);
            mTextPaint.setTextSize(this.getTextSize());
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTypeface(getTypeface());

            mLinePaint = new Paint();
            mLinePaint.setColor(this.getLineColor());
            mLinePaint.setStyle(Style.FILL);
        }

        this.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    setBackgroundDrawable(mBackgroundSelected);
                } else {
                    setBackgroundDrawable(mBackgroundUnselected);
                }
            }
        });

    }

    @Override
    public void onDraw(Canvas canvas) {
        String text = getText().toString();
        text = mAllCaps ? text.toUpperCase() : text;

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

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
    }

    public void setTextColorSelected(int textColorSelected) {
        this.mTextColorSelected = textColorSelected;
    }

    public void setTextColorUnselected(int textColor) {
        this.mTextColorUnselected = textColor;
    }
}
