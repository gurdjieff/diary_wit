package com.diary.activities;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import app.api.DiaryApi;
import app.api.Rest;
import app.diary.R;

import com.diary.activities.AddDiaryActivity.SubmitListener;
import com.diary.db.DBManager;
import com.diary.models.DiaryAdapter;
import com.diary.models.MyDiary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SearchDiaryActivity extends Activity {
	private ImageView back = null;
	private EditText search = null;
	private ListView searchInfo = null;
	private DBManager manager = null;
	public List <MyDiary> diaries = null;
	private Button searchBtn = null;

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
	private void init(){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_diary_info);
		manager = new DBManager(this);
		diaries = new ArrayList<MyDiary>();
		search = (EditText)this.findViewById(R.id.search_edit);
		back = (ImageView)this.findViewById(R.id.back_search_diary);
		searchInfo = (ListView)this.findViewById(R.id.search_diary_info_list);
		search.addTextChangedListener(new SearchInfoListener());
		
		searchBtn = (Button)this.findViewById(R.id.searchBtn);
		searchBtn.setOnClickListener(new SearchListener());
		
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	}
	
	class SearchListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			SharedPreferences preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);				
			String username=preferences.getString("username", null);
		    new SearchTask(SearchDiaryActivity.this).execute(username, search.getText().toString().trim()); 	
		    }
	}
	
	private class SearchTask extends AsyncTask<String, Void, List<MyDiary>> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public SearchTask(Context context)
		{
			this.context = context;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();	
			this.dialog = new ProgressDialog(context, 1);	
			this.dialog.setMessage("Retrieving Diary List");
			this.dialog.show();
		}

		@Override
		protected List<MyDiary> doInBackground(String... params) {

			try {
   				return (List<MyDiary>) DiaryApi.searchDiaire(params[0]+"/"+params[1]);
			}

			catch (Exception e) {
				Log.v("ASYNC", "ERROR : " + e);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<MyDiary> result) {
			super.onPostExecute(result);

			diaries = result;
//			refresh();
			if (dialog.isShowing())
				dialog.dismiss();
			
//			List<MyDiary> temp = new ArrayList<MyDiary>();
//			for(int i = 0; i < diaries.size(); i++) {
//				MyDiary diary = diaries.get(i);
//				if (!search.getText().toString().trim().equals("")) {
//					if (diary.getDiaryInfo().contains(search.getText().toString().trim())) {
//						temp.add(diary);
//					}			
//				}
//			}
			
			DiaryAdapter adapter = new DiaryAdapter(SearchDiaryActivity.this, diaries);
			searchInfo.setAdapter(adapter);
			searchInfo.setVerticalScrollBarEnabled(true);
			searchInfo.setOnItemClickListener(new ItemClickListener());
			searchInfo.setSelection(0);
			
			
		}
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
//			refresh();
		}
	}
	
	private void refresh(){
		List<MyDiary> temp = new ArrayList<MyDiary>();
		for(int i = 0; i < diaries.size(); i++) {
			MyDiary diary = diaries.get(i);
			if (!search.getText().toString().trim().equals("")) {
				if (diary.getDiaryInfo().contains(search.getText().toString().trim())) {
					temp.add(diary);
				}			
			}
		}
		
		DiaryAdapter adapter = new DiaryAdapter(this, temp);
		searchInfo.setAdapter(adapter);
		searchInfo.setVerticalScrollBarEnabled(true);
		searchInfo.setOnItemClickListener(new ItemClickListener());
		searchInfo.setSelection(0);
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
