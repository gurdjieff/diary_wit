package com.diary.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import app.diary.R;

public class AccessActivity extends Activity {
	private Button access;
	private SharedPreferences sp=null;
	private EditText password=null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.access);
		access = (Button)this.findViewById(R.id.access);
		access.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp=getSharedPreferences("pass", Context.MODE_PRIVATE);
				String passwordStr=sp.getString("password", null);
				Intent intent = null;

//				System.out.println(passwordStr);
				if (passwordStr == null) {
					intent = new Intent(AccessActivity.this, MainActivity.class);  
			        startActivity(intent);
			        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			        AccessActivity.this.finish();
					return;
				}
				LayoutInflater factory=LayoutInflater.from(AccessActivity.this);
				final View textEntry=factory.inflate(R.layout.check_password, null);
				AlertDialog.Builder builder=new AlertDialog.Builder(AccessActivity.this)
				.setTitle("check password")
				.setView(textEntry)
				.setPositiveButton("ok", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						password=(EditText)textEntry.findViewById(R.id.set_pass);
						sp=getSharedPreferences("pass", Context.MODE_PRIVATE);
						String passwd=sp.getString("password", null);
						System.out.println(password.getText().toString()+0);
						System.out.println(passwd+9);

						if (password.getText().toString().trim().equals(passwd)) {
							dialog.dismiss();
							Intent intent = null;
							intent = new Intent(AccessActivity.this, MainActivity.class);  
					        startActivity(intent);
					        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
					    }
						else {
							Toast.makeText(AccessActivity.this, "password is wrong", Toast.LENGTH_LONG).show();
						}
					}
				});
				builder.create().show();
			}
	
		});
	}
}
