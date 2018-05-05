package domel.ecampus.Component;

import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;

public class NoStartSpaceInputFilter implements InputFilter {

    boolean canEnterSpace = false;
    private AppCompatEditText editText;

    public NoStartSpaceInputFilter(AppCompatEditText editText){
        this.editText = editText;
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {

        if(editText.getText().toString().length() == 0)
        {
            canEnterSpace = false;
        }

        StringBuilder builder = new StringBuilder();

        for (int i = start; i < end; i++) {
            char currentChar = source.charAt(i);

            if (Character.isLetterOrDigit(currentChar) || currentChar == '_') {
                builder.append(currentChar);
                canEnterSpace = true;
            }

            if(Character.isWhitespace(currentChar) && canEnterSpace) {
                builder.append(currentChar);
            }


        }
        return builder.toString();
    }


}
