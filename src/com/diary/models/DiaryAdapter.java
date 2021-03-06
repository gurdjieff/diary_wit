package com.diary.models;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import app.diary.R;



public class DiaryAdapter extends BaseAdapter{
	private Activity activity;
	private LayoutInflater inflater;
	private List<MyDiary> diaries;
	public DiaryAdapter(Activity activity, List<MyDiary> diaries) {
		super();
		this.activity = activity;
		this.diaries = diaries;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return diaries.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return diaries.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			inflater = (LayoutInflater) activity.getLayoutInflater();
			convertView = inflater.inflate(R.layout.diary_info_view, null);
			holder = new ViewHolder();
			holder.diaryInfo = (TextView)convertView.findViewById(R.id.view_info);
			holder.diaryTitle = (TextView)convertView.findViewById(R.id.view_title);
			holder.date = (TextView)convertView.findViewById(R.id.view_date);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.diaryTitle.setText(activity.getString(R.string.title)+":"+diaries.get(position).getDiaryTitle());
		holder.diaryInfo.setText(diaries.get(position).getDiaryInfo());
//		holder.date.setText(diaries.get(position).getDate());
		return convertView;
	}
	class ViewHolder{
		TextView diaryTitle;
		TextView diaryInfo;
		TextView date;
	}
}
