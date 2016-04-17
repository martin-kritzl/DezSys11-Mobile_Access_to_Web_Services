package at.mkritzl.dezsys11.dezsys11.control;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import at.mkritzl.dezsys11.dezsys11.R;
import at.mkritzl.dezsys11.dezsys11.model.Response;
import at.mkritzl.dezsys11.dezsys11.utils.RestException;
import at.mkritzl.dezsys11.dezsys11.utils.RestHandler;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends UserActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.regsiter_button);
        Button backToLoginButton = (Button) findViewById(R.id.back_to_login_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        backToLoginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                redirect(LoginActivity.class);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        if (mAuthTask != null) {
            return;
        }

        if (checkInput()) {

            String email = mEmailView.getText().toString();
            String password = mPasswordView.getText().toString();

            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserRegisterTask(email, password);
            mAuthTask.execute((Void) null);

        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Response response;

        UserRegisterTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                this.response = RestHandler.register(getApplicationContext(), this.mEmail, this.mPassword);
            } catch (RestException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                if (this.response.getStatus() == 201) {
                    redirect(LoginActivity.class);
                } else if (this.response.getStatus()==403) {
                    mEmailView.setError(getString(R.string.error_user_already_assigned));
                } else if(response.getStatus()==0) {
                    Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_server_unreachable, Toast.LENGTH_LONG).show();
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

