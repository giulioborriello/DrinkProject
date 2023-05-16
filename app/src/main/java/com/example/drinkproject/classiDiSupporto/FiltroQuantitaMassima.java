package com.example.drinkproject.classiDiSupporto;

import android.text.InputFilter;
import android.text.Spanned;

public class FiltroQuantitaMassima implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int input = Integer.parseInt(dest.toString() + source.toString());
        if (input > 30) {
            return "";
        }
        return null;
    }
}
