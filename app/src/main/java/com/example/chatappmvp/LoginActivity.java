package com.example.chatappmvp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatappmvp.eventbus.CanceledEvent;
import com.example.chatappmvp.eventbus.PasswordErrorEvent;
import com.example.chatappmvp.eventbus.SuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCanceledEvent(CanceledEvent canceledEvent){
        showProgress(false);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasswordErrorEvent(PasswordErrorEvent passwordErrorEvent){
        showProgress(false);
        setPasswordError(passwordErrorEvent.getMessageResId());

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSuccessEvent(SuccessEvent successEvent) {
        showProgress(false);
        Toast.makeText(LoginActivity.this,"Basarili",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
