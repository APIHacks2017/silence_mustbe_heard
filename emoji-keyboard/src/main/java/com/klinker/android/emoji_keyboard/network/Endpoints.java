package com.klinker.android.emoji_keyboard.network;

import com.klinker.android.emoji_keyboard.models.NewsResponse;
import com.klinker.android.emoji_keyboard.models.TranslateResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by nirmal on 9/2/16.
 */
public interface Endpoints {

    public static String SERVER_BASEURL = "http://52.36.211.72:5555";
    final String gatewayAPIkey="c6c5e619-53c4-4ae8-b99d-05162e5f419e";
    final String newsAPIkey="708dc2592c9044e18c91187c72aaa03c";

    //    http://52.36.211.72:5555/gateway/Top%20Stories/2.0.0/sports.json
    @Headers({
            "Accept: application/json",
            "x-Gateway-APIKey: c6c5e619-53c4-4ae8-b99d-05162e5f419e"
    })
    @GET("/gateway/Top%20Stories/2.0.0/{type}.json?api-key="+newsAPIkey)
    Call<NewsResponse> getNews(@Path("type") String newsType);

//    https://www.googleapis.com/language/translate/v2?key=AIzaSyCrL8YYxBNTfv3AbqApWQyhoPhV9nipJl8&source=ta&target=en&q=நீ என்ன செய்து கொண்டிருக்கிற?
    @GET
    Call<TranslateResponse> translate(@Url String url, @Query("key") String key, @Query("source") String source,@Query("target") String target, @Query("q") String q);
}
