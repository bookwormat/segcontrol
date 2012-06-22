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
import android.util.AttributeSet;
import android.widget.RadioButton;
import at.bookworm.R;

/**
 * @author benjamin ferrari
 * @author Grantland Chew <grantlandchew@gmail.com>
 */
public class SegmentedControlButton extends RadioButton {

    private boolean mTextAllCaps;

    private int mLineColor;
    private int mLineHeightSelected;
    private int mLineHeightUnselected;

    private Paint mLinePaint;

    public SegmentedControlButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.segmentedControlButtonStyle);
    }

    public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    public int getLineColor() {
        return mLineColor;
    }

    public int getLineHeightUnselected() {
        return mLineHeightUnselected;
    }

    public void init(AttributeSet attrs, int defStyle) {
        if (attrs != null) {
            TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedControlButton, defStyle, R.style.Widget_Holo_SegmentedControl);

            mTextAllCaps = a.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            setTextCompat(getText());

            mLineColor = a.getColor(R.styleable.SegmentedControlButton_lineColor, 0);
            mLineHeightUnselected = a.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightUnselected, 0);
            mLineHeightSelected = a.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightSelected, 0);

            mLinePaint = new Paint();
            mLinePaint.setColor(getLineColor());
            mLinePaint.setStyle(Style.FILL);

            a.recycle();
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the line
        if (mLineColor != 0 && (mLineHeightSelected > 0 || mLineHeightUnselected > 0)) {
            int lineHeight;
            if (isChecked()) {
                lineHeight = mLineHeightSelected;
            } else {
                lineHeight = mLineHeightUnselected;
            }

            if (lineHeight > 0) {
                Rect rect = new Rect(0, this.getHeight() - lineHeight, getWidth(), this.getHeight());
                canvas.drawRect(rect, mLinePaint);
            }
        }
    }

    // Used for android:textAllCaps
    public void setTextCompat(CharSequence text) {
        if (mTextAllCaps) {
            setText(text.toString().toUpperCase());
        }
        else {
            setText(text);
        }
    }

    public void setLineColor(int lineColor) {
        this.mLineColor = lineColor;
    }
}
