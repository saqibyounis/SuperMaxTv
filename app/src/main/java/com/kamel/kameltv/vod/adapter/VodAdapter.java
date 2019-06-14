package com.kamel.kameltv.vod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kamel.kameltv.R;
import com.kamel.kameltv.channelcategory.ChannelCategory;
import com.kamel.kameltv.vod.model.VodCategoryModel;

import java.util.List;

public class VodAdapter extends BaseAdapter {
    Context context;
    List<VodCategoryModel> channelCategories;
    public VodAdapter(Context context, List<VodCategoryModel> channelCategories){
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
        textView.setText(channelCategories.get(position).getVategoryName());
        return convertView;    }
}
