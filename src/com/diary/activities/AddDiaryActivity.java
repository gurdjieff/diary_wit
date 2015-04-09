package com.diary.activities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.simple.JSONObject;
import app.api.DiaryApi;
import app.api.Rest;
import app.diary.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddDiaryActivity extends Activity {
	private TextView timeTextView = null;
	private TextView weekTextView = null;
	private Spinner weatherSpinner = null;
	private Button submit = null;
	private Calendar cal = Calendar.getInstance();
	private Date date = null;
	private SimpleDateFormat simpleDateFormat = null;
	public static final int WEEKDAYS = 7;
	private EditText diaryInfo = null;
	private EditText diaryTitle = null;
	public static String[] WEEK = { "monday", "tuesday", "wednesday", "thursday", "friday", "saturday",
			"sunday" };
	private ImageView back = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Rest.setup();
		init();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@SuppressLint("SimpleDateFormat")
	private void init() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_diary);
		date = cal.getTime();
		
		simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		timeTextView = (TextView) this.findViewById(R.id.time);
		timeTextView.setText(simpleDateFormat.format(date));
		weekTextView = (TextView) this.findViewById(R.id.week);
		weekTextView.setText(DateToWeek(date));
		weatherSpinner = (Spinner) this.findViewById(R.id.weather);
		diaryInfo = (EditText)this.findViewById(R.id.edit_diary_info);
		diaryTitle = (EditText)this.findViewById(R.id.edit_title);
		back = (ImageView)this.findViewById(R.id.back_add_diary);
		submit = (Button)this.findViewById(R.id.submit);
		submit.setOnClickListener(new SubmitListener());
		back.setOnClickListener(new BackListener());
		ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, 
				R.array.weather, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		weatherSpinner.setAdapter(adapter);
		weatherSpinner.setPrompt(getString(R.string.weather));
	}
	
	class SubmitListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			SharedPreferences preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);				
			String username=preferences.getString("username", null);
		    new AddDiary(AddDiaryActivity.this).execute(username,diaryTitle.getText().toString().trim(), diaryInfo.getText().toString()); 		}
	}
	
	public static String DateToWeek(Date date){
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayIndex < 1 || dayIndex > WEEKDAYS) {  
	        return null;  
	    }  
	    return WEEK[dayIndex - 1];  
	}
	
	class BackListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			back();
		}
	}
	
	private class AddDiary extends AsyncTask<String, Void, String> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public AddDiary(Context context)
		{
			this.context = context;
		}
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			this.dialog = new ProgressDialog(context, 1);	
			this.dialog.setMessage("add diary...");
			this.dialog.show();
		}
		
	
		@Override
		protected String doInBackground(String... params) {
            try {
            	JSONObject json = new JSONObject(); 
            	json.put("id", 0);
            	json.put("Diary_text", params[2]);
            	json.put("Diary_title", params[1]);
            	json.put("user_name", params[0]);

//            	Log.v("gurdjieff1", json.toJSONString());
    			return (String) DiaryApi.addDiary(json.toJSONString());
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
			
			if (result.equals("success")) {
				Toast.makeText(AddDiaryActivity.this, "submit success!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(AddDiaryActivity.this, "try it later!", Toast.LENGTH_SHORT).show();
			}
			Log.v("gurdjieff", "finished");
		}
	}
	

	private void back(){
			Intent intent = new Intent();
			intent.setClass(AddDiaryActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
	}
}
