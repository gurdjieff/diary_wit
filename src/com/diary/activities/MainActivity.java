package com.diary.activities;
//import com.facebook.AppEventsLogger;
//import com.facebook.FacebookAuthorizationException;
//import com.facebook.FacebookOperationCanceledException;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.FacebookDialog;
//import com.facebook.widget.LoginButton;
import com.facebook.Session;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

import app.diary.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
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
//	private Button feedbackButton = null;
//    private LoginButton loginButton;
    
   
//    private UiLifecycleHelper uiHelper;
//
//    private Session.StatusCallback callback = new Session.StatusCallback() {
//        @Override
//        public void call(Session session, SessionState state, Exception exception) {
//            onSessionStateChange(session, state, exception);
//        }
//    };
//    
//    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
//
//    }

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.diary_view);
		
//		 uiHelper = new UiLifecycleHelper(this, callback);
//	     uiHelper.onCreate(savedInstanceState);
		
		addDiary = (ImageButton) this.findViewById(R.id.diary_add);
		lookDiary = (ImageButton) this.findViewById(R.id.diary_look);
		searchDiary = (ImageButton) this.findViewById(R.id.diary_search);
		passDiary = (ImageButton) this.findViewById(R.id.diary_pass);
		exitDiary = (ImageButton) this.findViewById(R.id.diary_exit);
//		feedbackButton = (Button) this.findViewById(R.id.feedback_button);

		addDiary.setOnClickListener(new MyListener());
		lookDiary.setOnClickListener(new MyListener());
		searchDiary.setOnClickListener(new MyListener());
		passDiary.setOnClickListener(new MyListener());
		exitDiary.setOnClickListener(new MyListener());
//		feedbackButton.setOnClickListener(new MyListener());
//		
//		loginButton = (LoginButton) findViewById(R.id.login_button);
//        loginButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
//            @Override
//            public void onUserInfoFetched(GraphUser user) {
//            	if (user != null && user.getName() != null) {
//            		ParseObject facebookUser = new ParseObject("facebookUsers");
//                	facebookUser.put("userName", user.getName());
//                	facebookUser.saveInBackground();
//				}
//            }
//        });
	}

	
	
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	  super.onActivityResult(requestCode, resultCode, data);
//      uiHelper.onActivityResult(requestCode, resultCode, data, dialogCallback);
//	  ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
//	  
//	}
//	
//	private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
//        @Override
//        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
//            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
//        }
//
//        @Override
//        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
//            Log.d("HelloFacebook", "Success!");
//        }
//    };
	
	
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
	
	private void logout(){
	    // clear any user information
//	    mApp.clearUserPrefs();
	    // find the active session which can only be facebook in my app
	    Session session = Session.getActiveSession();
	    // run the closeAndClearTokenInformation which does the following
	    // DOCS : Closes the local in-memory Session object and clears any persistent 
	    // cache related to the Session.
	    session.closeAndClearTokenInformation();
	    // return the user to the login screen
	    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
	    // make sure the user can not access the page after he/she is logged out
	    // clear the activity stack
	    finish();
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
				SharedPreferences preferences=getSharedPreferences("loginState", Context.MODE_PRIVATE);
				Editor editor=preferences.edit();
				editor.putString("state", null);
				editor.commit();
				
				logout();
				
				
				
				intent = new Intent(MainActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
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
