package com.kamel.kameltv;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.ListViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.kamel.kameltv.category.CategoryModel;
import com.kamel.kameltv.channelcategory.ChannelCategory;
import com.kamel.kameltv.channelcategory.constants.Constants;
import com.kamel.kameltv.channels.ChannelAdapter;
import com.kamel.kameltv.channels.ChannelsModel;
import com.kamel.kameltv.channels.favouritechannels.FavouriteChannels;
import com.kamel.kameltv.m3u.M3UListConstants;
import com.kamel.kameltv.m3u.model.M3UItem;
//import com.kamel.kameltv.player.GiraftiVidoPlayer;
import com.kamel.kameltv.player.VLCActivity;
import com.kamel.kameltv.player.betterplayer.BetterPlayerActivity;
import com.kamel.kameltv.player.exoplayer.ExoPlayerActivity;
import com.kamel.kameltv.player.exoplayer.ExoPlayerFragment;
//import com.kamel.kameltv.player.ijk.IJKVidoPlayear;
//import com.kamel.kameltv.player.xplyer.XVideoPlayer;
//import com.kamel.kameltv.player.ijk.IJKVidoPlayear;
import com.kamel.kameltv.useaccount.UserAccount;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.playerlibrary.view.IjkVideoView;

import org.json.JSONArray;
import org.json.JSONException;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

import static android.media.ToneGenerator.MAX_VOLUME;

public class LiveTvActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener{
         static   GridView channelGrid;
           public static ChannelAdapter channelAdapter;
           public static List<ChannelsModel> channelList;
    public static String SAMPLE_URL = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_640x360.m4v";

           SearchView channelSearch;
    private long exitTime = 0;
  //  private IjkVideoView videoView;
    private ProgressDialog progressDialog;
    String url;
   static Context context;
    private AudioManager mAudioManager;
    private AdapterView.AdapterContextMenuInfo info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        showProgress();

        setContentView(R.layout.activity_live_tv);
       // videoView=findViewById(R.id.video_view);
    context=this;
        channelGrid=findViewById(R.id.channelgrid);
        if(Constants.channelCategories.get(0).getChanneles()!=null&&Constants.channelCategories.get(0).getChanneles().size()>0) {
            ////////////////////////////
         initView();

        }else{
            String url = "http://cuthecord.ddns.net:25461/player_api.php?username=" + UserAccount.userAccount.getUserName() + "&password=" + UserAccount.userAccount.getPassword() + "&action=get_live_streams";
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONArray array = new JSONArray(new String(responseBody));
                        for (int i = 0; i < array.length(); i++) {
                            String name = array.getJSONObject(i).getString("name");
                            String streamId = array.getJSONObject(i).getString("stream_id");
                            String streamIcon = array.getJSONObject(i).getString("stream_icon");
                            String epgChannelId = array.getJSONObject(i).getString("epg_channel_id");
                            String categoryId = array.getJSONObject(i).getString("category_id");
                            for (int j = 0; j < Constants.channelCategories.size(); j++) {
                                if (Constants.channelCategories.get(j).getCategoryId().equalsIgnoreCase(categoryId)) {
                                    Constants.channelCategories.get(j).getChanneles().add(new ChannelsModel(name, streamId, epgChannelId, categoryId, "", streamIcon));


                                }


                            }

                            initView();


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });


        }
        //////////////////



    }
    public void initView(){
          hideProgress();
        //mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        channelSearch=findViewById(R.id.channelsearch);
        channelSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                channelAdapter.filter(newText);


                return false;
            }
        });
        channelSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){

                    channelAdapter.filter("");

                }
            }
        });

        channelList=Constants.channelCategories.get(0).getChanneles();

        channelAdapter=new ChannelAdapter(this,channelList);
        channelGrid.setAdapter(channelAdapter);

        channelGrid.setDrawSelectorOnTop(true);
          //  videoView= findViewById(R.id.video_view);

          //  url = channelList.get(0);
         url="http://cuthecord.ddns.net:25461/live/"+UserAccount.userAccount.getUserName()+"/"+UserAccount.userAccount.getPassword()+"/71454"+".rtmp";

             //  videoView =  findViewById(R.id.video_view);

              /* videoView.setOnPreparedListener((MediaPlayer.OnPreparedListener) this);
               videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                   @Override
                   public boolean onError(MediaPlayer mp, int what, int extra) {
                       Toast.makeText(LiveTvActivity.this, "Please try again later!", Toast.LENGTH_LONG).show();
                      // finish();
                       return false;
                   }
               });*/


/*

               final Uri videoUri = Uri.parse(url);

               videoView.setVideoURI(videoUri);
*/
        registerForContextMenu(channelGrid);
       /*  videoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
             @Override
             public void onPrepared(IMediaPlayer iMediaPlayer) {
                 hideProgress();
                 videoView.start();
                 videoView.unmute();

             }
         });*/

///        runner.execute(url);

        channelGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


          String url="http://cuthecord.ddns.net:25461/live/"+UserAccount.userAccount.getUserName()+"/"+UserAccount.userAccount.getPassword()+"/"+channelList.get(position).getStreamId()+".ts";
                LiveTvActivity.this.url=url;

                final Uri videoUri = Uri.parse(url);
                com.kamel.kameltv.epg.model.constants.Constants.streamid=channelList.get(position).getStreamId();

                com.kamel.kameltv.epg.model.constants.Constants.size=channelList.size();
                  fullScreen(null,position);

            }
        });


//videoView.setVideoURI(Uri.parse(this.url));
//videoView.start();*/
hideProgress();

    }





    @Override
    protected void onPause() {
        super.onPause();
       // videoView.pause();
       // videoView.setVisibility(View.GONE );
    }

    @Override
    protected void onResume() {
        super.onResume();
      // // videoView.resume();
      //  videoView.setVisibility(View.VISIBLE);
    }

    public void onBackPressed() {
      /*  for (ChannelCategory category:  Constants.channelCategories) {
            category.getChanneles().clear();
        }*/


        super.onBackPressed();
    }



    @Override
    public void onPrepared(MediaPlayer mp) {
       hideProgress();
        //Starts the video playback as soon as it is ready
       // videoView.start();
    }

    public void showProgress() {
        progressDialog = new ProgressDialog(LiveTvActivity.this);
        progressDialog = ProgressDialog.show(LiveTvActivity.this, "Please Wait",
                "Loading...", true);
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public void fullScreen(View view,int position){
           //   videoView.release(true);
        channelGrid.setSelection(position);
        com.kamel.kameltv.epg.model.constants.Constants.position=position;
         com.kamel.kameltv.epg.model.constants.Constants.channelAdapters=channelAdapter;
         SAMPLE_URL= LiveTvActivity.this.url;
             Intent intent=new Intent(LiveTvActivity.this, VLCActivity.class);
          intent.putExtra("url",LiveTvActivity.this.url);
          intent.putExtra("index",position);
          startActivity(intent);
    }
public static void updateChannelList(List<ChannelsModel> list){
       channelList=list;
        channelAdapter=new ChannelAdapter(context,list);
       channelGrid.setAdapter(channelAdapter);
    System.out.println("INUPDATE"+list.size());
        channelAdapter.notifyDataSetChanged();



}
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        try {
            // Casts the incoming data object into the type for AdapterView objects.
            info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            // If the menu object can't be cast, logs an error.
            Log.e("Menu", "bad menuInfo", e);
            return;
        }        inflater.inflate(R.menu.chanelcontextmenu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // below variable info contains clicked item info and it can be null; scroll down to see a fix for it
      //  ChannelAdapter info = (ChannelAdapter) item.getMenuInfo();

      try {

        switch (item.getItemId()) {
            case R.id.addfavorite:

                //hidTesteItem(info.position);
                  ChannelsModel m3UItem= (ChannelsModel) channelList.get(info.position);
                  FavouriteChannels.databaseHelper.addChannel(m3UItem);
                 Toast.makeText(context, m3UItem.getName()+" Favourite Added!"+channelGrid.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();


                return true;
            case R.id.removefavorite:
                ChannelsModel m3UItemr= (ChannelsModel) channelList.get(info.position);
                FavouriteChannels.databaseHelper.deletechannel(m3UItemr);
                Toast.makeText(context, m3UItemr.getName()+" Removed from favourite!", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
      }catch (Exception ex){
          ex.printStackTrace();
          Toast.makeText(context, "Unable to add this into favorite", Toast.LENGTH_SHORT).show();


      }
return super.onContextItemSelected(item);
    }

    public void playChannel(String link){


    }
}
