package at.bookworm.widget;

import android.content.Context;
import android.util.AttributeSet;
import at.bookworm.R;

/**
 * @author Grantland Chew <grantlandchew@gmail.com>
 */
public class TabControlButton extends SegmentedControlButton {

    public TabControlButton(Context context, AttributeSet attrs) {
        super(context, attrs, R.attr.tabControlButtonStyle);
    }
}
