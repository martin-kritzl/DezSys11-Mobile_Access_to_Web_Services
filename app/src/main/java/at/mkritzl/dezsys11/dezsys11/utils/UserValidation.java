package at.mkritzl.dezsys11.dezsys11.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.LinkedList;
import java.util.List;

import at.mkritzl.dezsys11.dezsys11.R;

/**
 * Used for validating the entered email and password of the user
 */
public class UserValidation {

    /**
     * Checks the entered email. Must contain "@" and "." and length &lt;= 50
     *
     * @param email The entered email
     * @param context The application context
     * @return null if all is correct; String with the textual failure
     */
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

    /**
     * Checks the entered password. Must have a length &lt;= 50 and &gt;4
     *
     * @param password The entered password
     * @param context The application context
     * @return null if all is correct; String with the textual failure
     */
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

    /**
     * Used for connecting the failures with " & "
     *
     * @param errors a list of failures
     * @return The representing string
     */
    private static String beautifyError(List<String> errors) {
        if (errors.size()==0) return null;

        String output = "";
        for (String error: errors) {
            output+=" & " + error;
        }
        return output.replaceFirst(" & ", "");
    }
}
