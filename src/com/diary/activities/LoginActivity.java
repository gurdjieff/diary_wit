package com.diary.activities;


import com.diary.activities.MainActivity.MyListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.diary.R;

public class LoginActivity extends Activity {
	private Button loginBtn = null;

	private Button registerBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		loginBtn = (Button) this.findViewById(R.id.button1);
		registerBtn = (Button) this.findViewById(R.id.button2);
		
		loginBtn.setOnClickListener(new MyListener());
		registerBtn.setOnClickListener(new MyListener());


	}
	
	class MyListener implements OnClickListener {
		Intent intent = null;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
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
