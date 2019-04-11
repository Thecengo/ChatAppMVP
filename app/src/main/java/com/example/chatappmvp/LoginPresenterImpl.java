package com.example.chatappmvp;

import android.text.TextUtils;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void validateCredentials(String username, String password) {

        if(!TextUtils.isEmpty(password) && !isPasswordValid(password)){
            loginView.setPasswordError(R.string.error_invalid_password);
            return;
        }

        if(TextUtils.isEmpty(username)){
            loginView.setUsernameError(R.string.error_field_required);
            return;
        }
        else if(!isEmailValid(username)){
            loginView.setUsernameError(R.string.error_invalid_email);
            return;
        }

        loginView.showProgress(true);
        loginModel.login(username,password);

    }


    private boolean isPasswordValid(String pass){
        return pass.length() > 4;

    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }
}
