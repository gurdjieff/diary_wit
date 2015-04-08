package app.api;

import java.lang.reflect.Type;
import java.util.List;





import android.util.Log;

import com.diary.models.Diary;
import com.diary.models.MyDiary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DiaryApi {
	//////////////////////////////////////////////////////////////////////////////////	
	public static List<MyDiary> getAll(String name) {
		String json = Rest.getAll(name);
		Type collectionType = new TypeToken<List<MyDiary>>() {}.getType();
	   	Log.v("jsonjson", json);

		return new Gson().fromJson(json, collectionType);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public static Diary get(String call) {
		String json = Rest.get(call);
		Type objType = new TypeToken<Diary>(){}.getType();

		return new Gson().fromJson(json, objType);
	}
	
//////////////////////////////////////////////////////////////////////////////////
    public static String login(String info) {
	   	Log.v("gurdjieff77", info);
       String json = Rest.login(info);
       return json;
    }
    
    public static String register(String info) {
        String json = Rest.register(info);
        return json;
     }
	
    public static String addDiary(String info) {
        String json = Rest.addDiary(info);
        return json;
     }
    
	
	
	//////////////////////////////////////////////////////////////////////////////////	
	public static String delete(String call) {
		return Rest.delete(call);
	}
	//////////////////////////////////////////////////////////////////////////////////
	public static String insert(String call,Diary d) {		
		Type objType = new TypeToken<Diary>(){}.getType();
		String json = new Gson().toJson(d, objType);
  
		return Rest.post(call,json);
	}
}
