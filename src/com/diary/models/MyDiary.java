package com.diary.models;


//[{"id":0,"user_name":"medaymen","Diary_title":"2","Diary_text":"11"},{"id":0,"user_name":"medaymen","Diary_title":"3","Diary_text":"11"},{"id":0,"user_name":"medaymen","Diary_title":"4","Diary_text":"11"},{"id":0,"user_name":"medaymen","Diary_title":"6","Diary_text":"11"},{"id":0,"user_name":"medaymen","Diary_title":"7","Diary_text":"11"}]

public class MyDiary {
	
	private String Diary_title;
	private String Diary_text;
	private String user_name;

	private int id;
	
	public String getDiaryInfo() {
		return Diary_text;
	}
	
	public String getDiaryTitle() {
		return Diary_title;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "000";
	}
}
