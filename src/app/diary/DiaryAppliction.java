package app.diary;


import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

import android.app.Application;
import android.content.SharedPreferences;



public class DiaryAppliction extends Application {
    public DropboxAPI<AndroidAuthSession> mDBApi;
    private static final String APP_KEY = "o5ibaq1ztssayeg";
    private static final String APP_SECRET = "xkdbnzqbsqdjm0y";

    
    private static final String ACCOUNT_PREFS_NAME = "prefs";
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";
	  @Override
	  public void onCreate() {
	    super.onCreate();
	    AndroidAuthSession session = buildSession();
	    mDBApi = new DropboxAPI<AndroidAuthSession>(session);

	    // Initialize Crash Reporting.
	    ParseCrashReporting.enable(this);

	    // Enable Local Datastore.
	    Parse.enableLocalDatastore(this);
//
//	    // Add your initialization code here
	    Parse.initialize(this, "C6wKEYWYhEuTuLsmCzk4PZU20mCOqZRkhcJ2EwXg", "ayWeZRpmVaQ4M1ZNZoSWIj9AJ6I0RPEXjHZNwNUp");
//
//
	    ParseUser.enableAutomaticUser();
//	    ParseACL defaultACL = new ParseACL();
//	    // Optionally enable public read access.
//	     defaultACL.setPublicReadAccess(true);
//	    ParseACL.setDefaultACL(defaultACL, true);
	    

	    
	  }
	  
	  private void loadAuth(AndroidAuthSession session) {
	        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
	        String key = prefs.getString(ACCESS_KEY_NAME, null);
	        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
	        if (key == null || secret == null || key.length() == 0 || secret.length() == 0) return;

	        if (key.equals("oauth2:")) {
	            // If the key is set to "oauth2:", then we can assume the token is for OAuth 2.
	            session.setOAuth2AccessToken(secret);
	        } else {
	            // Still support using old OAuth 1 tokens.
	            session.setAccessTokenPair(new AccessTokenPair(key, secret));
	        }
	    }
	  
	  private AndroidAuthSession buildSession() {
	        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
	        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
	        loadAuth(session);
	        return session;
	    }
	}