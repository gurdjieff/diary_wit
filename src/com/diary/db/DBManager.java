


package com.diary.db;

import java.util.List;
import com.diary.models.Diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;

public class DBManager {
	private DataBaseHelper helper = null;

	public DBManager(Context context) {
		// TODO Auto-generated constructor stub
		helper = new DataBaseHelper(context);
	}
	public void insert(Diary diary){
//		SQLiteDatabase db = helper.getWritableDatabase();
//		ContentValues values = new ContentValues();
//		values.put("date", diary.getDate());
//		values.put("week", diary.getWeek());
//		values.put("weather", diary.getWeather());
//		values.put("diarytitle", diary.getDiaryTitle());
//		values.put("diaryinfo", diary.getDiaryInfo());
//		db.insert("DIARY_INFO", null, values);
//		db.close();
	}
	
	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("delete from DIARY_INFO where id = " + id);
		//System.out.println(db.delete("DIARY_INFO", "id=" + id, null));
		db.close();
	}
	public void query(List<Diary> diaries) {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("DIARY_INFO", null, null,
				null, null, null, "id desc");
		diaries.clear();
		while(cursor.moveToNext()){
			Diary diary = new Diary();
			String title = cursor.getString(cursor.getColumnIndex("diarytitle"));
			String info = cursor.getString(cursor.getColumnIndex("diaryinfo"));
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String week = cursor.getString(cursor.getColumnIndex("week"));
			String weather = cursor.getString(cursor.getColumnIndex("weather"));
			int id = cursor.getInt(cursor.getColumnIndex("id"));
//			diary.setDate(date);
//			diary.setWeek(week);
//			diary.setWeather(weather);
//			diary.setDiaryTitle(title);
//			diary.setDiaryInfo(info);
//			diary.setId(id);
//			diaries.add(diary);
		}
		cursor.close();
		db.close();
	}
	
	public void deleteAll() {
		String delete_sql="delete from DIARY_INFO";
		SQLiteDatabase db=helper.getWritableDatabase();
		db.execSQL(delete_sql);
		db.close();
	}
	
	public void dimSearch(EditText editText,List<Diary> diaries){
		SQLiteDatabase db=helper.getReadableDatabase();
		Cursor cursor = db.query("DIARY_INFO", null, "diaryinfo like '%" + editText.getText().toString() + "%'", null, null, null, "id desc");
		diaries.clear();
		while(cursor.moveToNext()){
			Diary diary = new Diary();
			String date = cursor.getString(cursor.getColumnIndex("date"));
			String week=cursor.getString(cursor.getColumnIndex("week"));
			String weather = cursor.getString(cursor.getColumnIndex("weather"));
			String diaryInfo=cursor.getString(cursor.getColumnIndex("diaryinfo"));
			String diaryTitle=cursor.getString(cursor.getColumnIndex("diarytitle"));
			int id = cursor.getInt(cursor.getColumnIndex("id"));
//			diary.setDate(date);
//			diary.setWeek(week);
//			diary.setWeather(weather);
//			diary.setDiaryInfo(diaryInfo);
//			diary.setDiaryTitle(diaryTitle);
//			diary.setId(id);
//			diaries.add(diary);
		}
		cursor.close();
		db.close();
	}
}

