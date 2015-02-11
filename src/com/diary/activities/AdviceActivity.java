package com.diary.activities;

import com.parse.ParseObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import app.diary.R;


public class AdviceActivity extends Activity {
	private ImageView back = null;
	private EditText userName = null;
	private EditText adviceInfo = null;
	/*private EditText emailPass = null;*/
	private Button submit = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}

	private void init() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advice);
		back = (ImageView) this.findViewById(R.id.back_advice);
		userName = (EditText) this.findViewById(R.id.user_name);
		/*emailPass = (EditText)this.findViewById(R.id.user_email_pass);*/
		adviceInfo = (EditText) this.findViewById(R.id.advice_info);
		submit = (Button) this.findViewById(R.id.submit);
		submit.setOnClickListener(new SubmitListener());
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	class SubmitListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if ( !userName.getText().toString().trim().equals("")
					&& !adviceInfo.getText().toString().trim().equals("")) {
				 ParseObject feedback = new ParseObject("Feedbacks");
				 feedback.put("userName", userName.getText().toString().trim());
				 feedback.put("adviceInfo", adviceInfo.getText().toString().trim());

				 feedback.saveInBackground();
				 finish();
					Toast.makeText(AdviceActivity.this, "thank you", Toast.LENGTH_SHORT).show();
			}else {
				Toast.makeText(AdviceActivity.this, getString(R.string.detail_info), Toast.LENGTH_SHORT).show();
			}
		}
	}
}
