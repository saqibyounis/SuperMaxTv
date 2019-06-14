package com.kamel.kameltv.series.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamel.kameltv.R;
import com.kamel.kameltv.series.model.SeasonModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SeasonAdapter extends BaseAdapter {
     List<SeasonModel> list;
     Context context;

    public SeasonAdapter(Context context, List<SeasonModel> list){

        this.context=context;
        this.list=list;
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
            convertView = layoutInflater.inflate(R.layout.seasoncard, null);
        }
        ImageView imageView=convertView.findViewById(R.id.seriesimage);
        TextView textView=convertView.findViewById(R.id.seriesname);


        if(list.get(position).getCover().isEmpty()){

            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.imagefound_90x90));


        }else {
            try {
                Picasso.get().load(list.get(position).getCover()).into(imageView);
            }catch (Exception ex){
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.imagefound_90x90));

            }

        }
        textView.setText(list.get(position).getName());



        return convertView;

    }
}
