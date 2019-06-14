package com.kamel.kameltv;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidstudy.networkmanager.Monitor;
import com.androidstudy.networkmanager.Tovuti;
import com.kamel.kameltv.category.CategoryAdapter;
import com.kamel.kameltv.category.CategoryModel;
import com.kamel.kameltv.channels.favouritechannels.FavouriteChannels;
import com.kamel.kameltv.multiplayer.mode.MultiPlayerModel;
import com.kamel.kameltv.multiviewfragment.MultiViewConstants;
import com.kamel.kameltv.multiviewfragment.MultiviewPlayer;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    TextView cinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_category);
        final GridView gridView=findViewById(R.id.catgrid);
       // gridView.setLayoutParams(new GridView.LayoutParams(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
      cinfo=findViewById(R.id.cinfo);
        Tovuti.from(this).monitor(new Monitor.ConnectivityListener(){
            @Override
            public void onConnectivityChanged(int connectionType, boolean isConnected, boolean isFast){
                // TODO: Handle the connection...
                String info;

                if(isFast){
                    info="Network is Fast ";


                }else{
                    info="Network is Slow ";

                }

                switch (connectionType){

                    case 0:
                        info+="Network type Mobile";
                        break;


                    case 1:
                        info+="Network type Wifi";
                        break;
                        default:
                            info+="Network type Unknown";
                             break;

                }

                cinfo.setText(info);



            }
        });

        final List<CategoryModel> catnames=new ArrayList<>();
        FavouriteChannels.initializeDatabase(this);
        catnames.add(new CategoryModel(R.drawable.livetv_90x90,"Live Tv"));
        catnames.add(new CategoryModel(R.drawable.series_90x90,"Series"));
        catnames.add(new CategoryModel(R.drawable.vod_90x90,"Vod"));
        catnames.add(new CategoryModel(R.drawable.speedtest,"Speed Test"));
        catnames.add(new CategoryModel(R.drawable.account_90x90,"Account"));
        catnames.add(new CategoryModel(R.drawable.favchannelicon_90x90,"Favourites"));
         catnames.add(new CategoryModel(R.drawable.refresh_90x90,"Refresh"));
        catnames.add(new CategoryModel(R.drawable.cleaner_90x90,"Cleaner"));
        catnames.add(new CategoryModel(R.drawable.youtube_90x90,"Youtube"));
        catnames.add(new CategoryModel(R.drawable.multiview90x90,"MultiView"));

        catnames.add(new CategoryModel(R.drawable.appstore90x90,"Appstore"));
        catnames.add(new CategoryModel(R.drawable.vpn_90x90,"VPN"));

        catnames.add(new CategoryModel(R.drawable.settings_90x90,"Settings"));

        final CategoryAdapter adapter=new CategoryAdapter(this,catnames);
        gridView.setNumColumns(4);
        gridView.setSelection(1);
        gridView.setAdapter(adapter);
        gridView.setDrawSelectorOnTop(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                CategoryModel model=catnames.get(position);
                String name=model.getName();
                switch (name){
                    case "Live Tv":
                         startActivity(new Intent(CategoryActivity.this,LiveTvActivity.class));
                          break;
                    case "Series":
                        startActivity(new Intent(CategoryActivity.this,SeriesActivity.class));
                        break;
                    case "Vod":

                        startActivity(new Intent(CategoryActivity.this,VODActvity.class));
                        break;

                    case "Account":

                        startActivity(new Intent(CategoryActivity.this,AccountActvity.class));
                        break;

                    case "Speed Test":

                        if(appInstalledOrNot("com.netflix.Speedtest")){
                            Intent intent =getPackageManager().getLaunchIntentForPackage("com.netflix.Speedtest");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_MAIN);
                            startActivity(intent);
                        }else{
                            String url = "http://thetivi.com/kensapks/speedtest.apk";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);

                        }
                        break;

                    case "Settings":
                        startActivity(new Intent(CategoryActivity.this,SettingsActivity.class));

                        break;
                    case "MultiView":
                        ArrayList<MultiPlayerModel> list=new ArrayList<>();
                        list.add(new MultiPlayerModel("Exo Player"));
                        list.add(new MultiPlayerModel("VLC Player"));
                        com.kamel.kameltv.multiplayer.adapter.CategoryAdapter adapter1=new com.kamel.kameltv.multiplayer.adapter.CategoryAdapter(CategoryActivity.this,list);

                        final Dialog dialog=new Dialog(CategoryActivity.this);
                        dialog.setContentView(R.layout.multiviewchanneldialog);
                        GridView gridView1=dialog.findViewById(R.id.channelgrid);
                        gridView1.setAdapter(adapter1);
                        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if(position==0){


                                    dialog.dismiss();
                                    startActivity(new Intent(CategoryActivity.this, MultiviewAcr.class));


                                }else {

                                    dialog.dismiss();
                                    startActivity(new Intent(CategoryActivity.this, MultiviewAcr2.class));



                                }
                            }
                        });

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.show();

                            }
                        });
                        break;
                    case "Favourites":
                        startActivity(new Intent(CategoryActivity.this,FavouriteActivity.class));


                         break;
                    case "Refresh":
                        startActivity(new Intent(CategoryActivity.this,RefreshActivity.class));
                           finish();

                        break;
                    case "Cleaner":
                        if(appInstalledOrNot("com.yunos.tv.defensor")){
                            Intent intent =getPackageManager().getLaunchIntentForPackage("com.yunos.tv.defensor");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_MAIN);
                            startActivity(intent);
                        }else{
                            String url = "http://thetivi.com/kensapks/rocketclean.apk";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);

                        }


                        break;
                    case "Youtube":
                         if(appInstalledOrNot("com.google.android.youtube.tv")){
                             Intent intent =getPackageManager().getLaunchIntentForPackage("com.google.android.youtube.tv");
                             intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                             intent.setAction(Intent.ACTION_MAIN);

                             startActivity(intent);

                         }else{
                             String url = "http://thetivi.com/kensapks/youtubetv.apk";
                             Intent i = new Intent(Intent.ACTION_VIEW);
                             i.setData(Uri.parse(url));
                             startActivity(i);

                         }

                        break;
                    case "Appstore":
                        if(appInstalledOrNot("cm.aptoidetv.pt")){
                            Intent intent =getPackageManager().getLaunchIntentForPackage("cm.aptoidetv.pt");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_MAIN);

                            startActivity(intent);

                        }else{
                            String url = "http://thetivi.com/kensapks/aptoidetv.apk";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);

                        }

                        break;

                    case "VPN":

                        if(appInstalledOrNot("com.simplona.vpn.proxy.server.fire.tablets.tv")){
                            Intent intent =getPackageManager().getLaunchIntentForPackage("com.simplona.vpn.proxy.server.fire.tablets.tv");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_MAIN);

                            startActivity(intent);

                        }else{
                            String url = "http://thetivi.com/kensapks/freevpn.apk";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);

                        }
                        break;


                }

                adapter.notifyDataSetChanged();
            }
        });

    }
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;
    }

    @Override
    protected void onStop() {
     //   Tovuti.from(this).stop();
        super.onStop();
    }
}
