package com.diary.activities;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import app.diary.R;

public class DetailDiaryInfoActivity extends Activity {
	private TextView title = null;
	private TextView date = null;
	private TextView info = null;
	private ImageView back = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		init();
	}
	private void init(){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_diary_info);
		title = (TextView)this.findViewById(R.id.detail_view_title);
		date = (TextView)this.findViewById(R.id.detail_view_date);
		info = (TextView)this.findViewById(R.id.detail_view_info);
		back = (ImageView)this.findViewById(R.id.back_detail_diary);
		Bundle bundle = this.getIntent().getExtras();
		title.setText(bundle.getString("title"));
		date.setText(bundle.getString("date")+"  "+bundle.getString("week")+"  "+bundle.getString("weather"));
		info.setText(bundle.getString("info"));
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
			}
		});
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
