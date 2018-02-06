package com.sopt.saver.saver.API;

import android.graphics.Typeface;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by kyi42 on 2017-07-04.
 */

public class ETCOperation {
    public static String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
    public static void setFontOnPicker(LinearLayout ll, Typeface typeface) {
        EditText et = (EditText) ll.getChildAt(1);
        et.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        et.setTypeface(typeface);
    }
}
