package com.diary.activities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.LoginButton;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.diary.activities.MainActivity.MyListener;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import app.diary.R;

public class LoginActivity extends Activity {
	private Button loginBtn = null;

	private Button registerBtn = null;
    private LoginButton loginButton;
    private UiLifecycleHelper uiHelper;
	private SharedPreferences preferences;
	private CheckBox checkbok = null;
	private EditText username = null;
	private EditText password = null;
	private String facebookUser = null;

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };
    
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {

    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		username = (EditText)findViewById(R.id.EditText01);
		password = (EditText)findViewById(R.id.editText2);
		loginBtn = (Button) this.findViewById(R.id.button1);
		registerBtn = (Button) this.findViewById(R.id.button2);
		
		loginBtn.setOnClickListener(new MyListener());
		registerBtn.setOnClickListener(new MyListener());
		uiHelper = new UiLifecycleHelper(this, callback);
	    uiHelper.onCreate(savedInstanceState);
	    checkbok = (CheckBox)findViewById(R.id.cb1);
		
		loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
            @Override
            public void onUserInfoFetched(GraphUser user) {
            	if (user != null && user.getName() != null) {
        			Toast.makeText(LoginActivity.this, "444", Toast.LENGTH_LONG).show();
        			facebookUser = user.getName();
				}
            }
        });
	}
	
	private class FacebookLogin extends AsyncTask<Object, Void, String> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public FacebookLogin(Context context)
		{
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			this.dialog = new ProgressDialog(context, 1);	
			this.dialog.setMessage("submit to Dropbox...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
            try {
            	return "";
			}
			catch (Exception e) {
				Log.v("Donate", "ERROR : " + e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			
			preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);
			Editor editor=preferences.edit();
			if (checkbok.isChecked()) {
				editor.putString("state", "login");
				editor.putString("state", "login");

			} else {
				editor.putString("state", facebookUser);
			}
			editor.commit();
		}
	}
	
	private class commonLogin extends AsyncTask<Object, Void, String> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public commonLogin(Context context)
		{
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			this.dialog = new ProgressDialog(context, 1);	
			this.dialog.setMessage("submit to Dropbox...");
			this.dialog.show();
		}

		@Override
		protected String doInBackground(Object... params) {
//            Log.v("gurdjieff", ""+params[0]);
            try {
            	return "";
			}
			catch (Exception e) {
				Log.v("Donate", "ERROR : " + e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
			
			preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);
			Editor editor=preferences.edit();
			if (checkbok.isChecked()) {
				
				editor.putString("state", "login");
				editor.putString("username", username.getText().toString());

			} else {
				editor.putString("state", null);
			}
			
			editor.commit();

			Intent intent = new Intent(LoginActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  super.onActivityResult(requestCode, resultCode, data);
      uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
	  Log.d("HelloFacebook", "Success!");
	  new FacebookLogin(LoginActivity.this).execute(); 

      Intent intent = new Intent(LoginActivity.this,
				MainActivity.class);
      startActivity(intent);
	  finish();
	}
	
	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
        @Override
        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
            Log.d("HelloFacebook", "Success!");
            Intent intent = new Intent(LoginActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();
        }
    };
	
	class MyListener implements OnClickListener {
		Intent intent = null;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
    		    new commonLogin(LoginActivity.this).execute(); 
				break;
			case R.id.button2:
				intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				finish();

				break;
			default:
				break;
			}
		}
	}
	
	
	public void login(View v){
		EditText edtphone = (EditText)findViewById(R.id.editText2);
		EditText edtpwd = (EditText)findViewById(R.id.EditText01);
		if(edtphone.getText().toString().equals("") && edtpwd.getText().toString().equals("")){
			Toast.makeText(getBaseContext(), "444", Toast.LENGTH_LONG).show();
		}else if(edtphone.getText().toString().equals("") || edtpwd.getText().toString().equals("")){
			Toast.makeText(getApplicationContext(), "3333!", Toast.LENGTH_LONG).show();
		}else {
			Toast.makeText(getBaseContext(), "3333:"+edtphone.getText().toString()+
					"\n3333:"+edtpwd.getText().toString(), Toast.LENGTH_LONG).show();
		}
	}
}
