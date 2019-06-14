package com.kamel.kameltv.multiviewfragment.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kamel.kameltv.R;
import com.kamel.kameltv.multiviewfragment.MultiViewConstants;
import com.kamel.kameltv.multiviewfragment.model.MultiViewModel;

import java.util.List;

public class MultiViewPlayerAdapter  extends BaseAdapter {
    List<Fragment> list;

    Context context;
    public MultiViewPlayerAdapter(Context context, List<Fragment> list){
        this.list=list;
     this.context=context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.multiviewplayer, null);
        }

        MultiViewConstants.layoutList.add(convertView);
    return convertView;
    }
}
