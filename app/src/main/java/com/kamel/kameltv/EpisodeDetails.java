package com.kamel.kameltv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.halilibo.bettervideoplayer.BetterVideoPlayer;
import com.kamel.kameltv.player.betterplayer.BetterPlayerActivity;
import com.kamel.kameltv.player.exoplayer.ExoPlayerActivity;
import com.kamel.kameltv.series.adapter.EpisodeAdapter;
import com.kamel.kameltv.series.constants.Contants;
import com.kamel.kameltv.series.model.SeasonModel;
import com.kamel.kameltv.useaccount.UserAccount;

public class EpisodeDetails extends AppCompatActivity {
       GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_details);

        gridView=findViewById(R.id.episodegrid);
        EpisodeAdapter episodeAdapter=new EpisodeAdapter(this,Contants.episodeModels);
        gridView.setAdapter(episodeAdapter);
        gridView.setDrawSelectorOnTop(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url="http://cuthecord.ddns.net:25461/series/"+ UserAccount.userAccount.getUserName()+"/"+UserAccount.userAccount.getPassword()+"/"+Contants.episodeModels.get(position).getId()+"."+Contants.episodeModels.get(position).getExtention();
                Intent intent=new Intent(EpisodeDetails.this, BetterPlayerActivity.class);
                intent.putExtra("url",url);

                startActivity(intent);

            }
        });

    }
}
