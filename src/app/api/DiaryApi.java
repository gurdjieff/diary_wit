package app.api;

import java.lang.reflect.Type;
import java.util.List;




import android.util.Log;

import com.diary.models.Diary;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DiaryApi {
	//////////////////////////////////////////////////////////////////////////////////	
	public static List<Diary> getAll(String call) {
		String json = Rest.get(call);
		Type collectionType = new TypeToken<List<Diary>>() {}.getType();
		
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
//    	Log.v("gurdjieff", info);

//        Type objType = new TypeToken<Diary>(){}.getType();

//        return new Gson().fromJson(json, objType);
        return "222";
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
