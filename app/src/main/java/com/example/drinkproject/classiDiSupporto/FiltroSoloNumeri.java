package com.example.drinkproject.classiDiSupporto;

import android.text.InputFilter;
import android.text.Spanned;

public class FiltroSoloNumeri implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuilder filteredBuilder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char character = source.charAt(i);
            if (Character.isDigit(character)) {
                filteredBuilder.append(character);
            }
        }
        return filteredBuilder.toString();
    }
}
