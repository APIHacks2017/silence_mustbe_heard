package com.klinker.android.emoji_keyboard.view;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.klinker.android.emoji_keyboard.EmojiKeyboardService;
import com.klinker.android.emoji_keyboard.Utility;
import com.klinker.android.emoji_keyboard.adapter.NewsAdapter;
import com.klinker.android.emoji_keyboard.models.NewsResponse;
import com.klinker.android.emoji_keyboard.network.Endpoints;
import com.klinker.android.emoji_keyboard_trial.R;

import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.internal.Util;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.klinker.android.emoji_keyboard.network.Endpoints.SERVER_BASEURL;

public class CustomKeyboardSinglePageView {

    private final Gson gson;
    private Context context;
    private BaseAdapter adapter;
    private Endpoints mAPI;

    public CustomKeyboardSinglePageView(Context context, BaseAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        gson = new GsonBuilder().
                setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
        this.mAPI = retrofit.create(Endpoints.class);

        Log.d("ui","init page view");
    }

    public View getView() {

        final RelativeLayout customPage = new RelativeLayout(context);
        LayoutInflater.from(context).inflate(R.layout.smart_asst, customPage);

        final ListView mList = (ListView) customPage.findViewById(R.id.newsList);
        String resp = Utility.getValueInPref(context,Utility.NEWS_DATA);

        if(resp==null) {
            Call<NewsResponse> call = mAPI.getNews("sports");
            call.enqueue(new Callback<NewsResponse>() {
                @Override
                public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                    if (response.code() == 200) {
                        final NewsResponse res = response.body();
                        Type type = new TypeToken<NewsResponse>() {}.getType();
                        String json = gson.toJson(res,type);
                        Log.d("ui", "Got the results =" + json);
                        Utility.saveKeyValueInPref(context, Utility.NEWS_DATA, json);
                        NewsAdapter mAdapter = new NewsAdapter((EmojiKeyboardService) context, res);
                        mList.setAdapter(mAdapter);
                    }
                }

                @Override
                public void onFailure(Call<NewsResponse> call, Throwable t) {
                    Log.d("ui", "enda run aala");
                }
            });
        }else{
            Log.d("ui", "Saved JSON = "+resp);
            Type type = new TypeToken<NewsResponse>() {}.getType();
            NewsResponse res = gson.fromJson(resp,type);
            NewsAdapter mAdapter = new NewsAdapter((EmojiKeyboardService) context, res);
            mList.setAdapter(mAdapter);
        }
        return customPage;
    }
}