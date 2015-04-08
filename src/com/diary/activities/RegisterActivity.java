package com.diary.activities;

import java.net.PasswordAuthentication;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;

import org.json.simple.JSONObject;

import com.diary.activities.LoginActivity.MyListener;

import android.R.string;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.LoginFilter.UsernameFilterGeneric;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import app.api.DiaryApi;
import app.diary.R;

public class RegisterActivity extends Activity {
	private ImageView back = null;
	EditText username = null;
	EditText password1 = null;
	EditText password2 = null;
	Button registerBtn = null;

	String name = null;
	String password = null;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		back = (ImageView) this.findViewById(R.id.back_look_diary);
		username = (EditText) this.findViewById(R.id.editText01);
		password1 = (EditText) this.findViewById(R.id.editText02);
		password2 = (EditText) this.findViewById(R.id.editText03);
		registerBtn = (Button) this.findViewById(R.id.registerBtn);


		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

				if (username.getText().toString().length() == 0) {
					Toast.makeText(RegisterActivity.this, "please input username", Toast.LENGTH_SHORT).show();
				} else if (password1.getText().toString().length() == 0) {
					Toast.makeText(RegisterActivity.this, "please input password", Toast.LENGTH_SHORT).show();
				} else if (!(password1.getText().toString().trim().equals(password2.getText().toString().trim()))) {
					Toast.makeText(RegisterActivity.this, "two password is not same!", Toast.LENGTH_SHORT).show();
				}  else {
				    new Register(RegisterActivity.this).execute(username.getText().toString().trim(), password1.getText().toString().trim()); 
				}
			}
		});

	}
	
	private class Register extends AsyncTask<Object, Void, String> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public Register(Context context)
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
            	JSONObject json = new JSONObject();  
            	json.put("name", params[0]);
            	json.put("password", params[1]);
//            	Log.v("gurdjieff1", json.toJSONString());
    
    			return (String) DiaryApi.register(json.toJSONString());
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
			
			Log.v("gurdjieff", "finished");

			
			SharedPreferences preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);
			Editor editor=preferences.edit();
				
			editor.putString("state", "login");
			editor.putString("username", username.getText().toString());

			editor.putString("state", null);
			
			editor.commit();

//			Intent intent = new Intent(RegisterActivity.this,
//					MainActivity.class);
//			startActivity(intent);
//			finish();
		}
	}
}
