package app.diary;


import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

import android.app.Application;



public class DiaryAppliction extends Application {

	  @Override
	  public void onCreate() {
	    super.onCreate();

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
	}