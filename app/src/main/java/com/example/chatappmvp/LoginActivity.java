package com.example.chatappmvp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;

    private AutoCompleteTextView mEmailView;
    private EditText editTextPass;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailView = findViewById(R.id.email);

        editTextPass = findViewById(R.id.password);

        mLoginFormView = findViewById(R.id.login_form);

        mProgressView = findViewById(R.id.login_progress);

        loginPresenter = new LoginPresenterImpl(this);


    }

    public void onClickAttemptLogin(View view){
        attemptLogin();

    }

    private void attemptLogin(){

        mEmailView.setError(null);
        editTextPass.setError(null);

        String email = mEmailView.getText().toString();
        String password = editTextPass.getText().toString();

        loginPresenter.validateCredentials(email,password);

    }

    @Override
    public void showProgress(final boolean showProgress) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(showProgress ? View.GONE :View.VISIBLE);

        mLoginFormView.animate().setDuration(shortAnimTime).alpha(showProgress ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(showProgress ? View.VISIBLE : View.GONE);

            }
        });
    }

    @Override
    public void setUsernameError(int messageResId) {

        mEmailView.setError(getString(messageResId));
        mEmailView.requestFocus();

    }


    @Override
    public void setPasswordError(int messageResId) {
        editTextPass.setError(getString(messageResId));
        editTextPass.requestFocus();

    }

    @Override
    public void successAction() {
        Toast.makeText(LoginActivity.this,"Basarili",Toast.LENGTH_LONG).show();

    }
}
