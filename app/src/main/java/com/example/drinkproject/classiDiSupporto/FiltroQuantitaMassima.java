package com.example.drinkproject.classiDiSupporto;

import android.text.InputFilter;
import android.text.Spanned;

public class FiltroQuantitaMassima implements InputFilter {
    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        String inputStr = dest.toString() + source.toString();
        if (inputStr.matches("\\d+")) {  // Controlla se la stringa contiene solo cifre.
            int input = Integer.parseInt(inputStr);
            if (input > 30) {
                return "";
            }
        }
        return null;
    }

}
