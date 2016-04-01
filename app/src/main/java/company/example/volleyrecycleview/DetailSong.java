package company.example.volleyrecycleview;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import company.example.volleyrecycleview.Model.Song;

/**
 * Sources
 * play and pause in the same button: http://stackoverflow.com/a/18120296
 * change icon when they clciked : http://stackoverflow.com/a/15052724
 *
 */
public class DetailSong extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private ImageView   mImageDetail;
    private TextView    mArtist;
    private TextView     mAlbum;
    private TextView    mDescription;
    private boolean     isPlaying = false;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mMediaPlayer.stop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);
        //Getting the intent
        Intent intent = getIntent();
        Song song = (Song)intent.getParcelableExtra("song");
        Log.d("PEPE", song.toString());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(song.getTrackName());
        setSupportActionBar(toolbar);



        setInfo(song);
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        if(song.getPreviewUrl()!= null || song.getPreviewUrl()!="") {
            try {
                mMediaPlayer.setDataSource(song.getPreviewUrl());
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlaying) {
                    fab.setImageResource(R.drawable.ic_pause_white_36dp);
                    mMediaPlayer.start();
                    isPlaying = !isPlaying; // reverse

                } else {
                    fab.setImageResource(R.drawable.ic_play_arrow_white_36dp);
                    mMediaPlayer.pause();
                    isPlaying = !isPlaying; // reverse
                }

            }
    });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setInfo(Song song) {
        // Initializing the variables
        mImageDetail = (ImageView) findViewById(R.id.detailImage);
        mArtist      = (TextView)findViewById(R.id.artistName);
        mAlbum       = (TextView)findViewById(R.id.album);
        mDescription = (TextView)findViewById(R.id.description);

        String url = song.getArtWork().substring(0,song.getArtWork().length()- 13);
        Picasso.with(this).load(url+"500x500bb.jpg")
                .placeholder(R.mipmap.default_placeholder)
                .error(R.mipmap.default_placeholder)
                .into(mImageDetail);
        mArtist.setText(song.getArtistName());
        mAlbum.setText(song.getCollectionName());
        mDescription.setText(song.getWrapperType());
    }
}
