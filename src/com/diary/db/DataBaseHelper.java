package com.diary.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class Constant {
	public final static String TABLE_INFO = "create table DIARY_INFO"
			+ "(date TEXT,week TEXT,weather TEXT,diarytitle TEXT,diaryinfo TEXT,id INTEGER PRIMARY KEY AUTOINCREMENT)";
}

public class DataBaseHelper extends SQLiteOpenHelper{
	private static final int VERSION=1;
	public static final String DBNAME = "diary.db";
	public DataBaseHelper (Context context){
		super(context,DBNAME,null,VERSION);
	}
	
	public DataBaseHelper (Context context,int version){
		super(context,DBNAME,null,version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(Constant.TABLE_INFO);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
