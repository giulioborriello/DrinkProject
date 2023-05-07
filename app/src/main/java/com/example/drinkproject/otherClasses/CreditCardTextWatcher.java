package com.example.drinkproject.otherClasses;

import android.text.Editable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CreditCardTextWatcher implements TextWatcher {
    private EditText editText;

    public CreditCardTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String input = s.toString();
        StringBuilder inputFormattato = new StringBuilder();

        String soloNumeri = input.replaceAll("\\D", "");

        for (int i = 0; i < soloNumeri.length(); i++) {
            if (i % 4 == 0 && i != 0) {
                inputFormattato.append(" ");
            }
            inputFormattato.append(soloNumeri.charAt(i));
        }

        // Set the formatted text back to the EditText
        editText.removeTextChangedListener(this);
        editText.setText(inputFormattato.toString());
        editText.setSelection(inputFormattato.length());
        editText.addTextChangedListener(this);
    }
}

