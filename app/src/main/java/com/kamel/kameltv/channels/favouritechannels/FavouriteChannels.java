package com.kamel.kameltv.channels.favouritechannels;

import android.content.Context;
import android.provider.ContactsContract;

import com.kamel.kameltv.channels.ChannelsModel;
import com.kamel.kameltv.m3u.model.M3UItem;
import com.kamel.kameltv.sqlitedatabase.DatabaseHelper;

import java.util.List;

public class FavouriteChannels {

    public static List<ChannelsModel> favList;
    public static DatabaseHelper databaseHelper=null;
     public static void initializeDatabase(Context context){
         databaseHelper=new DatabaseHelper(context);
         favList=databaseHelper.getAllChannel();


     }


}
