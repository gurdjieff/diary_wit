package com.diary.activities;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONObject;

import com.diary.db.DBManager;
import com.diary.models.Diary;
import com.diary.models.DiaryAdapter;
import com.diary.models.MyDiary;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.ProgressListener;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;















import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import app.api.DiaryApi;
import app.api.Rest;
//import app.activities.DonationAdapter;
//import app.api.DonationApi;
import app.diary.DiaryAppliction;
import app.diary.R;




public class LookDiaryActivity extends Activity {
	private ImageView back = null;
	private ListView diaryInfo = null;
	private DBManager manager = null;
//	private List<Diary> diaries = null;
    private boolean mLoggedIn;
    private static final String TAG = "LookDiaryActivity";
    public int listPostion = 0;
	public static List <MyDiary> diaries = new ArrayList<MyDiary>();


	
	private static final String APP_KEY = "o5ibaq1ztssayeg";
    private static final String APP_SECRET = "xkdbnzqbsqdjm0y";

    
    private static final String ACCOUNT_PREFS_NAME = "prefs";
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    private static final boolean USE_OAUTH1 = false;

    DropboxAPI<AndroidAuthSession> mApi;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Rest.setup();
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
        AndroidAuthSession session = mApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                session.finishAuthentication();
                storeAuth(session);
                setLoggedIn(true);
            } catch (IllegalStateException e) {
            	 showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                 Log.i(TAG, "Error authenticating", e);
             }
         }
	}
	
	private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }
	
	
	private void init() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.diary_info);
		manager = new DBManager(this);
		diaries = new ArrayList<MyDiary>();
		back = (ImageView) this.findViewById(R.id.back_look_diary);
		diaryInfo = (ListView) this.findViewById(R.id.diary_info_list);
		DiaryAppliction app = (DiaryAppliction)getApplication();
	    mApi = app.mDBApi;
		refresh();
//		myDialog();
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		SharedPreferences preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);				
		String username=preferences.getString("username", null);
		new GetAllTask(this).execute(username);
	}
	
	
	
	
	private class GetAllTask extends AsyncTask<String, Void, List<MyDiary>> {

		protected ProgressDialog 		dialog;
		protected Context 				context;

		public GetAllTask(Context context)
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
				
//            	Log.v("gurdjieff1", json.toJSONString());
   				return (List<MyDiary>) DiaryApi.getAll(params[0]);
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
//			MyDiary m = (MyDiary)diaries[0];
//		   	Log.v("toStringtoStringtoString", diaries.get(0).getDiaryTitle());
//		   	Log.v("toStringtoStringtoString", "size"+diaries.size());

//            Log.v("Error authenticating", ""+diaries.toString());

//			DiaryAdapter adapter = new DiaryAdapter(context, diaries);
//			listView.setAdapter(adapter);
		   	
		   	
		   	String json = "[{\"user_name\":\"222\", \"Diary_title\":\"title\", \"Diary_text\":\"content\", \"id\":\"222\"},{\"user_name\":\"222\", \"Diary_title\":\"222\", \"Diary_text\":\"222\", \"id\":\"222\"}]";
			Type collectionType = new TypeToken<List<MyDiary>>() {}.getType();
			diaries = new Gson().fromJson(json, collectionType);
		   	Log.v("jsonjson", ""+diaries.size());
		   	
		   	
			
			DiaryAdapter adapter = new DiaryAdapter(LookDiaryActivity.this, diaries);
			diaryInfo.setAdapter(adapter);
			diaryInfo.setVerticalScrollBarEnabled(true);
			diaryInfo.setOnItemClickListener(new ItemClickListener());
			diaryInfo.setOnItemLongClickListener(new ItemLongPressListener());
			diaryInfo.setSelection(0);

			if (dialog.isShowing())
				dialog.dismiss();
		}
	}
	
	
	
	private void refresh (){
//		new GetAllTask(this).execute("/getall");
//		manager.query(diaries);
//		DiaryAdapter adapter = new DiaryAdapter(this, diaries);
//		diaryInfo.setAdapter(adapter);
//		diaryInfo.setVerticalScrollBarEnabled(true);
//		diaryInfo.setOnItemClickListener(new ItemClickListener());
//		diaryInfo.setOnItemLongClickListener(new ItemLongPressListener());
//		diaryInfo.setSelection(0);
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
							finish();
						}
					});
			builder.setNegativeButton(getString(R.string.cancel),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							finish();
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
//			intent.putExtra("date", diaries.get(position).getDate());
//			intent.putExtra("week", diaries.get(position).getWeek());
//			intent.putExtra("weather", diaries.get(position).getWeather());
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
					getString(R.string.backups) },
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
										LookDiaryActivity.this,
										getString(R.string.delete_over),
										Toast.LENGTH_SHORT)
										.show();
								refresh();
	
							} else if (which == 1) {
								dialog.dismiss();
//								refresh();		
								dropboxInit(position);
							} else if (which == 2) {
								dialog.dismiss();
								manager.deleteAll();
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
	    
	    
	    private void storeAuth(AndroidAuthSession session) {
	        // Store the OAuth 2 access token, if there is one.
	        String oauth2AccessToken = session.getOAuth2AccessToken();
	        if (oauth2AccessToken != null) {
	            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	            Editor edit = prefs.edit();
	            edit.putString(ACCESS_KEY_NAME, "oauth2:");
	            edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
	            edit.commit();
	            return;
	        }
	        // Store the OAuth 1 access token, if there is one.  This is only necessary if
	        // you're still using OAuth 1.
	        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
	        if (oauth1AccessToken != null) {
	            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	            Editor edit = prefs.edit();
	            edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
	            edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
	            edit.commit();
	            return;
	        }
	    }
	    
	    
	private void dropboxInit (int position){
    	listPostion = position;		    
		mLoggedIn = false;
		 AndroidAuthSession session = mApi.getSession();
		 if (session.authenticationSuccessful()) {
	            try {
	                session.finishAuthentication();
	                storeAuth(session);
	                setLoggedIn(true);
	            } catch (IllegalStateException e) {
	            	 showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
	                 Log.i(TAG, "Error authenticating", e);
	             }
	         }
		
	    if (mLoggedIn) {
		    new DropboxSubmit(this).execute(); 
		} else {
            if (USE_OAUTH1) {
                mApi.getSession().startAuthentication(LookDiaryActivity.this);
            } else {
                mApi.getSession().startOAuth2Authentication(LookDiaryActivity.this);
            }
        }
	}
	
	 private class DropboxSubmit extends AsyncTask<Object, Void, String> {

			protected ProgressDialog 		dialog;
			protected Context 				context;

			public DropboxSubmit(Context context)
			{
				this.context = context;
			}
			
			@Override
			protected void onPreExecute() {
				super.onPreExecute();	
				this.dialog = new ProgressDialog(context, 1);	
				this.dialog.setMessage("submit to Dropbox...");
				this.dialog.show();
			}

			@Override
			protected String doInBackground(Object... params) {
				
                Log.v("gurdjieff", ""+params[0]);

				String res = null;
				try {
					String fileName = diaries.get(
							listPostion)
							.getDiaryTitle();
					String info = diaries.get(
							listPostion)
							.getDiaryTitle()
							+ "\n" + diaries.get(listPostion)
									.getDiaryInfo();
			    	String fileContents = info;
			    	System.out.println(fileName);
					ByteArrayInputStream inputStream = new ByteArrayInputStream(fileContents.getBytes());
					try {
						
					    Entry newEntry = mApi.putFile("/diaries/" + fileName +".txt", inputStream,
					    		fileContents.length(), null, new ProgressListener(){
								@Override
								public void onProgress(long total, long part) {
									if(total == part){
									}
								}
					    });
					    Log.i("DbExampleLog", "The uploaded file's rev is: " + newEntry.rev);
					} catch (DropboxUnlinkedException e) {
					    Log.e("DbExampleLog", "User has unlinked.");
					} catch (DropboxException e) {
					    Log.e("DbExampleLog", "Something went wrong while uploading.");
					} finally {
					    if (inputStream != null) {
					        try {
					            inputStream.close();
					        } catch (IOException e) {
					        }
					    }
					}

				}
			  	catch(Exception e)
			      {
			    	e.printStackTrace();
			      }
				return res;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				
				
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				Toast toast = Toast.makeText(LookDiaryActivity.this, "save successsful", Toast.LENGTH_SHORT);
			     toast.show();
			}
		}
	
	
    private void setLoggedIn(boolean loggedIn) {
    	mLoggedIn = loggedIn;
    }
}

class TimeString {
	public static String getTime(){
		long now = System.currentTimeMillis(); 
        Calendar calendar = Calendar.getInstance();  
        calendar.setTimeInMillis(now);
        String time = calendar.get(Calendar.YEAR)+""+(calendar.get(Calendar.MONTH) + 1)+
        		""+calendar.get(Calendar.DATE)+""+calendar.get(Calendar.HOUR)+""+
        		calendar.get(Calendar.MINUTE)+""+calendar.get(Calendar.SECOND);
		return time;
	}
}

