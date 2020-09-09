package com.enzorobaina.synclocalandremotedb.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.List;

public class ViewUtils {
    public static void animateView(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
            .setDuration(duration)
            .alpha(show ? toAlpha : 0)
            .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    view.setVisibility(toVisibility);
                }
            });
    }

    public static String getString(EditText editText){
        return editText.getText().toString().trim();
    }

    public static int getInt(EditText editText){
        return Integer.parseInt(editText.getText().toString());
    }

    public static boolean fieldsAreOk(List<EditText> fields){
        HashMap<String, String> fieldErrors = new HashMap<String, String>(){
            {
                put("empty", "Este campo não pode estar vazio");
                put("invalid", "Este valor é inválido");
                put("invalid-numeric", "Este valor precisa estar entre 1 e 20");
            }
        };
        boolean isOk = true;
        EditText toBeFocused = null;

        for (EditText field : fields){
            field.setError(null);
            if (ViewUtils.getString(field).isEmpty()){
                field.setError(fieldErrors.get("empty"));
                isOk = false;
                if (toBeFocused == null){
                    toBeFocused = field;
                }
            }
            if (field.getInputType() == InputType.TYPE_CLASS_NUMBER){
                int intValue = ViewUtils.getInt(field);
                if (intValue < 1 || intValue > 20){
                    field.setError(fieldErrors.get("invalid-numeric"));
                    isOk = false;
                    if (toBeFocused == null){
                        toBeFocused = field;
                    }
                }
            }
        }
        if (toBeFocused != null){
            toBeFocused.requestFocus();
        }
        return isOk;
    }
}
