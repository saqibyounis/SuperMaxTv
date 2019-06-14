package com.kamel.kameltv.series;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamel.kameltv.LiveTvActivity;
import com.kamel.kameltv.R;
import com.kamel.kameltv.category.CategoryModel;
import com.kamel.kameltv.channelcategory.ChannelCategory;
import com.kamel.kameltv.channelcategory.ChannelCategoryAdapter;
import com.kamel.kameltv.m3u.M3UListConstants;
import com.kamel.kameltv.m3u.model.M3UItem;
import com.kamel.kameltv.series.model.SeriesCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class SeriesCategoryAdapter extends BaseAdapter {
    Context context;
    List<SeriesCategoryModel> channelCategories;
    public SeriesCategoryAdapter(Context context, List<SeriesCategoryModel> channelCategories){
        this.context=context;
        this.channelCategories=channelCategories;

    }
    @Override
    public int getCount() {
        return channelCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return channelCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.channelcatcard, null);
        }
        TextView textView=convertView.findViewById(R.id.textView2);
        textView.setText(channelCategories.get(position).getCategoryName());
        return convertView;    }
}
