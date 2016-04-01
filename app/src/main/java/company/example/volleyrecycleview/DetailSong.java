package company.example.volleyrecycleview;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
    private boolean     isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Getting the intent
        Intent intent = getIntent();
        Song song = (Song)intent.getParcelableExtra("song");
        Log.d("PEPE",song.toString());

        setInfo(song);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    fab.setImageResource(R.drawable.ic_pause_white_36dp);
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    fab.setBackgroundResource(R.drawable.ic_play_arrow_white_36dp);
                }
                isPlaying = !isPlaying; // reverse
            }

    });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setInfo(Song song) {
        // Initializing the variables
        mImageDetail = (ImageView) findViewById(R.id.detailImage);
    }
}
