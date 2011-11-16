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
package at.bookworm.widget.segcontrol;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/** @author benjamin ferrari */
public class SegmentedControlButton extends RadioButton {

    private int borderColor;

    private int lineColor;

    private int lineHeightSelected;

    private int lineHeightUnselected;

    private float mX;

    private boolean showBorder;

    private int textColorSelected;

    private int textColorUnselected;

    private int textDistanceFromLine;

    public SegmentedControlButton(Context context) {
        super(context);
    }

    public SegmentedControlButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SegmentedControlButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public int getBorderColor() {
        return borderColor;
    }

    public int getButtonWidth() {
        RadioGroup parent = (RadioGroup) this.getParent();
        int childCount = parent.getChildCount();
        int parentWidth = parent.getWidth();
        return parentWidth / childCount;
    }

    public int getLineColor() {
        return lineColor;
    }

    public int getLineHeightSelected() {
        return lineHeightSelected;
    }

    public int getLineHeightUnselected() {
        return lineHeightUnselected;
    }

    public int getTextColorSelected() {
        return textColorSelected;
    }

    public int getTextColorUnselected() {
        return textColorUnselected;
    }

    public void init(AttributeSet attrs) {
        TypedArray attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.SegmentedControlButton);
        this.borderColor = attributes.getColor(R.styleable.SegmentedControlButton_borderColor, 0);
        this.lineColor = attributes.getColor(R.styleable.SegmentedControlButton_lineColor, 0);
        this.textColorUnselected = attributes.getColor(R.styleable.SegmentedControlButton_textColorUnselected, 0);
        this.textColorSelected = attributes.getColor(R.styleable.SegmentedControlButton_textColorSelected, 0);
        this.showBorder = attributes.getBoolean(R.styleable.SegmentedControlButton_showBorder, true);
        this.lineHeightUnselected = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightUnselected, 0);
        this.lineHeightSelected = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_lineHeightSelected, 0);
        this.textDistanceFromLine = attributes.getDimensionPixelSize(R.styleable.SegmentedControlButton_textDistanceFromLine, 0);
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    @Override
    public void onDraw(Canvas canvas) {

        this.setWidth(getButtonWidth());

        String text = this.getText().toString();
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(this.getTextSize());
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint linePaint = new Paint();
        linePaint.setColor(this.getLineColor());
        linePaint.setStyle(Style.FILL);

        Paint borderPaint = new Paint();
        borderPaint.setColor(this.getBorderColor());
        borderPaint.setStyle(Style.STROKE);

        int lineHeight;
        if (isChecked()) {
            lineHeight = this.getLineHeightSelected();
            textPaint.setColor(this.getTextColorSelected());
        } else {
            lineHeight = this.getLineHeightUnselected();
            textPaint.setColor(this.getTextColorUnselected());
        }

        int textHeightPos = this.getHeight() - this.getLineHeightSelected() - getTextDistanceFromLine();

        canvas.drawText(text, mX, textHeightPos, textPaint);

        Rect rect = new Rect(0, this.getHeight() - lineHeight, getWidth(), this.getHeight());
        canvas.drawRect(rect, linePaint);

        if (this.showBorder) {
            Rect border = new Rect(0, 5, getWidth() - 1, this.getHeight() - 1);
            canvas.drawRect(border, borderPaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        super.onSizeChanged(w, h, ow, oh);
        mX = w * 0.5f; // remember the center of the screen
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public void setTextColorSelected(int textColorSelected) {
        this.textColorSelected = textColorSelected;
    }

    public void setTextColorUnselected(int textColor) {
        this.textColorUnselected = textColor;
    }

    public int getTextDistanceFromLine() {
        return textDistanceFromLine;
    }

    public void setTextDistanceFromLine(int textDistanceFromLine) {
        this.textDistanceFromLine = textDistanceFromLine;
    }

}
