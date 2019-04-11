package com.example.chatappmvp;

import android.os.AsyncTask;

public class LoginModelImpl implements LoginModel {

    private OnLoginFinishedListener mListener;

    private static final String [] DUMY_CREDENTIALS = new String[]{
            "cengiz@cengiz.com:asdf1","android@android.com:1adsa"
    };

    @Override
    public void login(String username, String password, OnLoginFinishedListener listener) {

        this.mListener = listener;

        new UserLoginTask(username,password).execute((Void) null);

    }

    public class UserLoginTask extends AsyncTask<Void,Void,Boolean>{

        private final String mEmail;
        private final String mPassword;

        public UserLoginTask(String mEmail, String mPassword) {
            this.mEmail = mEmail;
            this.mPassword = mPassword;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                Thread.sleep(2000);

            }catch (InterruptedException e){
                return false;

            }

            for (String credetiantal : DUMY_CREDENTIALS){

                String [] pieces = credetiantal.split(":");

                if (pieces[0].equals(mEmail)){

                    return pieces[1].equals(mPassword);
                }
            }
            return false;
        }
        @Override
        protected void onPostExecute(final Boolean success){

            if(success){
                mListener.onSuccess();
            }
            else {
                mListener.onPasswordError();
            }

        }

        protected void onCancelled(){
            mListener.onCanceled();
        }
    }


}
