package com.diary.activities;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;
import app.diary.R;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_login);
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
