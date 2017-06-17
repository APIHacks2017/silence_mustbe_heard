package com.klinker.android.emoji_keyboard.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.klinker.android.emoji_keyboard.EmojiKeyboardService;
import com.klinker.android.emoji_keyboard.models.NewsResponse;
import com.klinker.android.emoji_keyboard_trial.R;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter{

    protected EmojiKeyboardService emojiKeyboardService;

    NewsResponse mData;

    public NewsAdapter(EmojiKeyboardService emojiKeyboardService, NewsResponse data ) {
        this.emojiKeyboardService = emojiKeyboardService;
        this.mData = data;
    }

    @Override
    public int getCount() {
        return mData.getResults().size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView imageView;
        final TextView title;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.emojiKeyboardService).inflate(R.layout.news_article,null);
            imageView = (ImageView) convertView.findViewById(R.id.thumb);
            title = (TextView) convertView.findViewById(R.id.newsTitle);
        } else {
            imageView = (ImageView) convertView.findViewById(R.id.thumb);
            title = (TextView) convertView.findViewById(R.id.newsTitle);
        }

//        imageView.setImageResource(mData.getResults().get(position).getMultimedia().get(0).getUrl());
        imageView.setBackgroundResource(R.drawable.btn_background);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emojiKeyboardService.sendText(mData.getResults().get(position).getUrl());
            }
        });

        title.setText(mData.getResults().get(position).getTitle());

        return convertView;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
