package domel.ecampus.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import domel.ecampus.Base.BaseActivity;
import domel.ecampus.Model.User;
import domel.ecampus.MyApplication;
import domel.ecampus.R;

public class LoginActivity  extends BaseActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AppCompatEditText et_email;
    private AppCompatEditText et_password;
    private View v_progress;
    private View v_form;

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "administrador@salleurl.edu:123qwe"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        v_form = findViewById(R.id.login_form);
        v_progress = findViewById(R.id.login_progress);
        et_email = (AppCompatEditText) findViewById(R.id.email);
        et_password = (AppCompatEditText) findViewById(R.id.password_et);
        et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button submit = (Button) findViewById(R.id.submit_button);
        if (submit != null) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    attemptLogin();
                }
            });
        }


    }




    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            v_form.setVisibility(show ? View.GONE : View.VISIBLE);
            v_form.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v_form.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            v_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            v_progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    v_progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            v_progress.setVisibility(show ? View.VISIBLE : View.GONE);
            v_form.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private void attemptLogin() {

        //if there's an authentication process going on do not launch ignore form submit
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        et_email.setError(null);
        et_password.setError(null);

        // Store values at the time of the login attempt.
        String user = et_email.getText().toString();
        String password = et_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            et_password.setError(getString(R.string.error_invalid_password));
            focusView = et_password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(user)) {
            et_email.setError(getString(R.string.error_field_required));
            focusView = et_email;
            cancel = true;
        } else if (!isEmailValid(user)) {
            et_email.setError(getString(R.string.error_invalid_email));
            focusView = et_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            AppCompatCheckBox checkbox = (AppCompatCheckBox)findViewById(R.id.remember);
            showProgress(true);
            mAuthTask = new UserLoginTask(user, password,
                    checkbox != null && checkbox.isChecked());
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String email;
        private final String password;
        private final boolean remembered;

        UserLoginTask(String email, String password, boolean remembered) {
            this.email = email;
            this.password = password;
            this.remembered = remembered;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            //here would go network access

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(email)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(password);
                }
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                // we store a user with all the info but on a real situation that would be just a
                // UserPasswordToken to authenticate against DB
                getApp().setRemembered(remembered);
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                et_password.setError(getString(R.string.error_incorrect_password));
                et_password.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

}