package com.klinker.android.emoji_keyboard.adapter;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter{

    protected EmojiKeyboardService emojiKeyboardService;

    NewsResponse mData;
    private DisplayImageOptions imOptions;

    public NewsAdapter(EmojiKeyboardService emojiKeyboardService, NewsResponse data ) {
        this.emojiKeyboardService = emojiKeyboardService;
        this.mData = data;
        initImageLoader(emojiKeyboardService);
    }

    private void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb
//                .memoryCacheExtraOptions(240, 400) // default = device screen dimensions
                .diskCacheExtraOptions(240, 400, null)
                .discCacheFileCount(30)
                .memoryCacheSizePercentage(12)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        imOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .considerExifParams(true)
                .showImageForEmptyUri(R.drawable.loading)
                .showImageOnFail(R.drawable.loading)
                .showImageOnLoading(R.drawable.loading)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

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
        ImageLoader.getInstance().displayImage(mData.getResults().get(position).getMultimedia().get(0).getUrl(), imageView, imOptions);
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
