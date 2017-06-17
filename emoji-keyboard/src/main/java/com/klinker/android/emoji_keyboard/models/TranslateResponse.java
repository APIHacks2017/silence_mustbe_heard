
package com.klinker.android.emoji_keyboard.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TranslateResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
