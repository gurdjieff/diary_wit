package com.diary.activities;

import java.util.ArrayList;
import java.util.List;

import com.diary.db.DiaryDao;
import com.diary.models.Diary;
import com.diary.models.DiaryAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import app.diary.R;



public class LookDiaryActivity extends Activity {
	private ImageView back = null;
	private ListView diaryInfo = null;
	private DiaryDao diaryDao = null;
	private List<Diary> diaries = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	private void init() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.diary_info);
		diaryDao = new DiaryDao(this);
		diaries = new ArrayList<Diary>();
		back = (ImageView) this.findViewById(R.id.back_look_diary);
		diaryInfo = (ListView) this.findViewById(R.id.diary_info_list);
		refresh();
		myDialog();
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.push_below_in,
						R.anim.push_below_out);
			}
		});
	}
	
	private void refresh (){
		diaryDao.query(diaries);
		DiaryAdapter adapter = new DiaryAdapter(this, diaries);
		diaryInfo.setAdapter(adapter);
		diaryInfo.setVerticalScrollBarEnabled(true);
		diaryInfo.setOnItemClickListener(new ItemClickListener());
		diaryInfo.setOnItemLongClickListener(new ItemLongPressListener());
		diaryInfo.setSelection(0);
	}

	private void myDialog() {
		if (diaries.isEmpty() || diaries.size() < 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.prompt));
			builder.setMessage(getString(R.string.is_add_diary));
			builder.setPositiveButton(getString(R.string.ok),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setClass(LookDiaryActivity.this,
									AddDiaryActivity.class);
							startActivity(intent);
							overridePendingTransition(R.anim.push_up_in,
									R.anim.push_up_out);
							finish();
						}
					});
			builder.setNegativeButton(getString(R.string.cancel),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
							overridePendingTransition(R.anim.push_below_in,
									R.anim.push_below_out);
						}
					});
			builder.setCancelable(false);
			builder.create().show();
		}
	}

	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(LookDiaryActivity.this,
					DetailDiaryInfoActivity.class);
			intent.putExtra("title", diaries.get(position).getDiaryTitle());
			intent.putExtra("info", diaries.get(position).getDiaryInfo());
			intent.putExtra("date", diaries.get(position).getDate());
			intent.putExtra("week", diaries.get(position).getWeek());
			intent.putExtra("weather", diaries.get(position).getWeather());
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);
		}
	}

	class ItemLongPressListener implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View v,
				final int position, long id) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					LookDiaryActivity.this);
			builder.setTitle(getString(R.string.op));
			builder.setItems(new String[] {getString(R.string.delete),
					getString(R.string.backups), getString(R.string.empty) },
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							if (which == 0) {
								dialog.dismiss();
								diaryDao.delete(diaries.get(
										position).getId());
								System.out.println("id ->"
										+ diaries.get(position)
												.getId());
								Toast.makeText(
										LookDiaryActivity.this,
										getString(R.string.delete_over),
										Toast.LENGTH_SHORT)
										.show();
								refresh();
	
							} else if (which == 1) {
								dialog.dismiss();
								refresh();								
							} else if (which == 2) {
								dialog.dismiss();
								diaryDao.deleteAll();
								Toast.makeText(
										LookDiaryActivity.this,
										getString(R.string.emptyed),
										Toast.LENGTH_SHORT)
										.show();
								refresh();

							}
						}
					});
			builder.create().show();
			return true;
		}
	}
}
