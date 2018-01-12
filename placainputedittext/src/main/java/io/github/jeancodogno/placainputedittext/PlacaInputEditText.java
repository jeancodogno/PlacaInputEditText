package io.github.jeancodogno.placainputedittext;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;


public class PlacaInputEditText extends TextInputEditText {

    private int positioning[] = { 0, 1, 2, 3, 5, 6, 7, 8, 9};
    private boolean isUpdating = false;

    public PlacaInputEditText(Context context) {
        super(context);
        this.initialization();
    }

    public PlacaInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialization();
    }

    public PlacaInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initialization();
    }

    private void initialization() {

        this.setKeyListener(new NumberKeyListener() {

            public int getInputType() {
                return InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS;

            }

            @Override
            protected char[] getAcceptedChars() {
                return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J','K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'  };

            }
        });

        this.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(PlacaInputEditText.this.isUpdating){

                    PlacaInputEditText.this.isUpdating = false;
                    return;

                }

                String current = s.toString();

                String cleanPlate = PlacaInputEditText.this.getPlateClean(current);


                int length = cleanPlate.length();

                String padded = new String(cleanPlate);
                for (int i = 0; i < 7 - cleanPlate.length(); i++)
                    padded += " ";

                String letters = padded.substring(0, 3);
                String numbers = padded.substring(3, 7).replaceAll("[A-Z]", "");
                String plate = letters+"-"+numbers;

                PlacaInputEditText.this.isUpdating = true;

                PlacaInputEditText.this.setText(plate, TextInputEditText.BufferType.EDITABLE);
                PlacaInputEditText.this.setSelection(positioning[length]);

            }
        });

    }

    private String getPlateClean(String plate) {
        plate = plate.replaceAll("-", "").replaceAll(" ", "");

        if (plate.length() > 7)
            plate = plate.substring(0, 7);

        return plate;
    }

}
