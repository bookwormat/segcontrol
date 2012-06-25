# Segmented control button for Android

Implementation of a segmented control button for Android. A Segmented
Control is really a better looking and more touch friendly version of
a RadioButton.  In fact, this implementation inherits from Android's
default RadioButton.

The controls are customizable and themed in the fashion of the stock
controls in Android 4.0 (Ice Cream Sandwich) and above.

The project contains a sample activity that shows how the buttons are
implemented and styled.

![Screenshot](https://github.com/grantland/segcontrol/raw/master/segcontrol_screen1.png "Screenshot")

## Usage

You can use this library either with a theme or without a theme. With a theme is much easier but
here are examples for both.

### Theme

You can add the widget styles to your `Theme` in `themes.xml` and whenever you use either
`SegmentedControlButton` or `TabControlButton` the theme will be automatically set!

`values-v11/themes.xml`:

    <style name="CustomTheme" parent="@android:style/Theme.Holo">
        <item name="segmentedControlButtonStyle">@style/Widget.Holo.SegmentedControl</item>
        <item name="tabControlButtonStyle">@style/Widget.Holo.TabControl</item>
    </style>

`values/themes.xml`:

    <style name="CustomTheme" parent="@android:style/Theme">
        <item name="segmentedControlButtonStyle">@style/Widget.Holo.SegmentedControl</item>
        <item name="tabControlButtonStyle">@style/Widget.Holo.TabControl</item>
    </style>

And in your layout:

    <RadioGroup
        android:id="@+id/buttongroup1"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal" >

        <at.bookworm.widget.TabControlButton
            android:id="@+id/option1"
            android:text="Button 1" />

        <at.bookworm.widget.TabControlButton
            android:id="@+id/option2"
            android:text="Button 2" />
    </RadioGroup>

### No Theme

Instead of RadioButton, use either `SegmentedControlButton` or `TabControlButton` and set the widget's
style to either `Widget.Holo.SegmentedControlButton` or `Widget.Holo.TabControlButton`. Since you
aren't using a theme, you will have to set the style on every `SegmentedControlButton` and
`TabControlButton`.

In your layout:

    <RadioGroup
        android:id="@+id/buttongroup1"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal" >

        <at.bookworm.widget.TabControlButton
            android:id="@+id/option1"
            android:text="Button 1"
            style="@style/Widget.Holo.TabControlButton" />

        <at.bookworm.widget.TabControlButton
            android:id="@+id/option2"
            android:text="Button 2"
            style="@style/Widget.Holo.TabControlButton" />
    </RadioGroup>



