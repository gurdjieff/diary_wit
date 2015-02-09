package com.diary.activities;
import app.diary.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	private ImageButton addDiary = null;
	private ImageButton lookDiary = null;
	private ImageButton searchDiary = null;
	private ImageButton passDiary = null;
	private ImageButton exitDiary = null;
	private Button feedbackButton = null;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.diary_view);
		addDiary = (ImageButton) this.findViewById(R.id.diary_add);
		lookDiary = (ImageButton) this.findViewById(R.id.diary_look);
		searchDiary = (ImageButton) this.findViewById(R.id.diary_search);
		passDiary = (ImageButton) this.findViewById(R.id.diary_pass);
		exitDiary = (ImageButton) this.findViewById(R.id.diary_exit);
		feedbackButton = (Button) this.findViewById(R.id.feedback_button);

		addDiary.setOnClickListener(new MyListener());
		lookDiary.setOnClickListener(new MyListener());
		searchDiary.setOnClickListener(new MyListener());
		passDiary.setOnClickListener(new MyListener());
		exitDiary.setOnClickListener(new MyListener());
		feedbackButton.setOnClickListener(new MyListener());

		
	}
	
	private void exitDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("exit");
		builder.setMessage("are you sure to exit?");
		builder.setPositiveButton("ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				});
		builder.setNegativeButton("cancel",
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.setCancelable(false);
		builder.create().show();
	}
	
	class MyListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.diary_add:
				Intent intent = new Intent(MainActivity.this,
						AddDiaryActivity.class);
				startActivity(intent);
				break;
			case R.id.diary_look:
				intent = new Intent(MainActivity.this, LookDiaryActivity.class);
				startActivity(intent);
				break;
			case R.id.diary_search:
				intent = new Intent(MainActivity.this,
						SearchDiaryActivity.class);
				startActivity(intent);
				break;
			case R.id.diary_pass:
				intent = new Intent(MainActivity.this,
						SetPasswordActivity.class);
				startActivity(intent);
				break;
			case R.id.diary_exit:
				exitDialog();
				break;
			case R.id.feedback_button:
				intent = new Intent(MainActivity.this,
						AdviceActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	}
}
