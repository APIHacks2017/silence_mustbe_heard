package com.klinker.android.emoji_keyboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Utility {
    public static final ArrayList<Integer> initArrayList(int... ints) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i : ints)
        {
            list.add(i);
        }
        return list;
    }


    public static final String NEWS_DATA = "news";
    public static final String EVENTS_DATA = "events";
    public static String getValueInPref(Context mContext, String key){
        SharedPreferences preferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getString(key,null);
    }


    public static void saveKeyValueInPref(Context mContext, Map<String,String> kVals) {
        SharedPreferences preferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        Iterator it = kVals.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            prefEditor.putString((String)pair.getKey(), (String)pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
        prefEditor.commit();
    }

    public static void saveKeyValueInPref(Context mContext, String key, String val) {
        SharedPreferences preferences = mContext.getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putString(key, val);
        Log.d("ui","Saved data = "+val);
        prefEditor.commit();
    }
}
