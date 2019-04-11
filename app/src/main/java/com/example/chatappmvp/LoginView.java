package com.example.chatappmvp;

public interface LoginView {

    void showProgress(boolean showProgress);

    void setUsernameError(int messageResId);

    void setPasswordError(int messageResId);

    //void successAction();
}
