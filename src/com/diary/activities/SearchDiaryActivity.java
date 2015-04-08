package com.diary.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import app.diary.R;

import com.diary.db.DBManager;
import com.diary.models.Diary;
import com.diary.models.DiaryAdapter;

public class SearchDiaryActivity extends Activity {
	private ImageView back = null;
	private EditText search = null;
	private ListView searchInfo = null;
	private DBManager manager = null;
	private List<Diary> diaries = null;
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_diary_info);
		manager = new DBManager(this);
		diaries = new ArrayList<Diary>();
		search = (EditText)this.findViewById(R.id.search_edit);
		back = (ImageView)this.findViewById(R.id.back_search_diary);
		searchInfo = (ListView)this.findViewById(R.id.search_diary_info_list);
		search.addTextChangedListener(new SearchInfoListener());
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	class SearchInfoListener implements TextWatcher{

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			if (!search.getText().toString().trim().equals("")) {
				refresh();
			}else {
				diaries.clear();
				InputMethodManager manager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
				manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}
	
	private void refresh(){
//		manager.dimSearch(search,diaries);
//		DiaryAdapter adapter = new DiaryAdapter(this, diaries);
//		searchInfo.setAdapter(adapter);
//		searchInfo.setVerticalScrollBarEnabled(true);
//		searchInfo.setOnItemClickListener(new ItemClickListener());
//		searchInfo.setOnItemLongClickListener(new ItemLongPressListener());
//		searchInfo.setSelection(0);
	}
	
	class ItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(SearchDiaryActivity.this,
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
					SearchDiaryActivity.this);
			builder.setTitle(getString(R.string.op));
			builder.setItems(new String[] {getString(R.string.delete), getString(R.string.empty) },
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(final DialogInterface dialog,
								int which) {
							// TODO Auto-generated method stub
							if (which == 0) {
								dialog.dismiss();
								manager.delete(diaries.get(
										position).getId());
								System.out.println("id ->"
										+ diaries.get(position)
												.getId());
								Toast.makeText(
										SearchDiaryActivity.this,
										getString(R.string.delete_over),
										Toast.LENGTH_SHORT)
										.show();
								refresh();
	
							}  else if (which == 1) {
								dialog.dismiss();
								manager.deleteAll();
								Toast.makeText(
										SearchDiaryActivity.this,
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
