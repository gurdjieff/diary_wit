package com.diary.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import app.diary.R;

public class SetPasswordActivity extends Activity {
	private TextView digitalPass=null;
	private TextView noPass;
	private EditText setPass=null;
	private EditText confirmPass=null;
	private SharedPreferences preferences;
	private ImageView back = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void init(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_pass_way);
		digitalPass=(TextView)findViewById(R.id.digitalpass);
		digitalPass.setOnClickListener(new DigitalPassListener());
		noPass=(TextView)findViewById(R.id.nopass);
		noPass.setOnClickListener(new NoPassListener());
		back = (ImageView)this.findViewById(R.id.back_pass_diary);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.push_below_in, R.anim.push_below_out);
			}
		});
	}
	
	class DigitalPassListener implements OnClickListener{
		public void onClick(View v) {
			LayoutInflater factory=LayoutInflater.from(SetPasswordActivity.this);
			final View textEntry=factory.inflate(R.layout.digital_pass, null);
			AlertDialog.Builder builder=new AlertDialog.Builder(SetPasswordActivity.this)
			.setTitle(getString(R.string.set_password))
			.setView(textEntry)
			.setPositiveButton("set", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					setPass=(EditText)textEntry.findViewById(R.id.set_pass);
					confirmPass=(EditText)textEntry.findViewById(R.id.confirm_pass);
					if (!confirmPass.getText().toString().trim().equals("")&&
							confirmPass.getText().toString().trim().equals(setPass.getText().toString().trim())) {
						preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
						Editor editor=preferences.edit();
						editor.putString("password", setPass.getText().toString().trim());
						editor.commit();
						dialog.dismiss();
						Toast.makeText(SetPasswordActivity.this, R.string.pass_set_success, Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(SetPasswordActivity.this, R.string.dif_pass, Toast.LENGTH_LONG).show();
					}
				}
			})
			.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
		}
	}
	
	class NoPassListener implements OnClickListener{
		public void onClick(View v) {
			preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
			Editor editor=preferences.edit();
			editor.putString("passway", null);
			editor.commit();
			finish();
			overridePendingTransition(R.anim.push_below_in, R.anim.push_below_out);
			Toast.makeText(SetPasswordActivity.this, R.string.pass_cancel, Toast.LENGTH_LONG).show();
		}
	}
}
