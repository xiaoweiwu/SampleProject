package com.squareup.timessquare;

import android.view.ContextThemeWrapper;
import android.widget.TextView;

public class DefaultDayViewAdapter implements DayViewAdapter {
    @Override
    public void makeCellView(CalendarCellView parent) {
        TextView textView = new TextView(
                new ContextThemeWrapper(parent.getContext(), R.style.CalendarCell_CalendarDateCustom));
        textView.setDuplicateParentStateEnabled(true);
        parent.addView(textView);
        parent.setDayOfMonthTextView(textView);
    }
}
