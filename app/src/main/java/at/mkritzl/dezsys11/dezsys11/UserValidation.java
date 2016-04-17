package at.mkritzl.dezsys11.dezsys11;

import android.content.Context;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mkritzl on 17.04.2016.
 */
public class UserValidation {

    public static String isEmailValid(String email, Context context) {
        List<String> errors = new LinkedList<String>();
        if (TextUtils.isEmpty(email))
            return context.getString(R.string.error_email_empty);
        if (!email.contains("@")) {
            errors.add(context.getString(R.string.error_email_must_contain_at));
        }
        if (!email.contains(".")) {
            errors.add(context.getString(R.string.error_email_must_contain_dot));
        }
        if (email.length()>50) {
            errors.add(context.getString(R.string.error_email_too_long));
        }
        return beautifyError(errors);
    }

    public static String isPasswordValid(String password, Context context) {
        List<String> errors = new LinkedList<String>();
        if (TextUtils.isEmpty(password))
            return context.getString(R.string.error_password_empty);
        if (password.length()<5) {
            errors.add(context.getString(R.string.error_password_too_short));
        }
        if (password.length()>50) {
            errors.add(context.getString(R.string.error_password_too_long));
        }
        return beautifyError(errors);
    }

    private static String beautifyError(List<String> errors) {
        if (errors.size()==0) return null;

        String output = "";
        for (String error: errors) {
            output+=" & " + error;
        }
        return output.replaceFirst(" & ", "");
    }
}
