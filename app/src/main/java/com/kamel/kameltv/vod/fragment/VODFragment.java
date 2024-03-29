package com.kamel.kameltv.vod.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.kamel.kameltv.R;
import com.kamel.kameltv.SeriesActivity;
import com.kamel.kameltv.VODActvity;
import com.kamel.kameltv.channelcategory.ChannelCategory;
import com.kamel.kameltv.m3u.M3UListConstants;
import com.kamel.kameltv.m3u.model.M3UItem;
import com.kamel.kameltv.series.SeriesCategoryAdapter;
import com.kamel.kameltv.series.constants.Contants;
import com.kamel.kameltv.vod.Constats.Constants;
import com.kamel.kameltv.vod.adapter.VodAdapter;
import com.kamel.kameltv.vod.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class VODFragment  extends Fragment {
    ProgressDialog progressDialog;
    List<MovieModel> channelList=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         * Inflate the layout for this fragment
         */

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Wait..");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        View view =inflater.inflate(R.layout.fragment_channel_category,container,false);

        final GridView gridView=view.findViewById(R.id.grid);
        gridView.setNumColumns(1);
        gridView.setDrawSelectorOnTop(true);
        VodAdapter adapter=new VodAdapter(getActivity(),Constants.vodCategory);

        gridView.setAdapter(adapter);
        gridView.setDrawSelectorOnTop(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("Wait..");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                channelList=Constants.vodCategory.get(position).getMoviles();


                       VODActvity.updateChannelList(channelList);

                progressDialog.dismiss();
            }
        });
        progressDialog.dismiss();
        return view;

    }
   /* private  List<ChannelCategory> getChannelCategoris() {
        List<ChannelCategory> list=new ArrayList<>();
        for(String string: M3UListConstants.channelCategories){

            for(String vodCategory :Constants.vodCategory) {
                if (string.contains(vodCategory)) {
                    System.out.println("adding movie");

                    list.add(new ChannelCategory(string));
                }
            }
        }
        return list;
    }*/

}
