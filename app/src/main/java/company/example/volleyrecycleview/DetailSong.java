package company.example.volleyrecycleview;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import company.example.volleyrecycleview.Adapter.MyAdapter;
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
    private RecyclerView mRV;
    private GridLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private List<Song>  mObjects = new ArrayList<>();;
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
        //setting RecyclerView
        mRV = (RecyclerView)findViewById(R.id.mRV);
        mRV.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRV.setLayoutManager(mLayoutManager);
        
        callServer(song.getCollectionName());
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

        String url = song.getArtWork().substring(0,song.getArtWork().length()- 13);
        Picasso.with(this).load(url+"500x500bb.jpg")
                .placeholder(R.mipmap.default_placeholder)
                .error(R.mipmap.default_placeholder)
                .into(mImageDetail);
        mArtist.setText(song.getArtistName());
        mAlbum.setText(song.getCollectionName());
    }
    private void callServer(final String word) {
        // Instantiate the RequestQueue.

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            //http://stackoverflow.com/a/10786112
            url = "http://itunes.apple.com/search?term="+ URLEncoder.encode(word, "UTF-8")+"&limit=3";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.

                        try {
                            Log.d("MAINACTIVITY",response.toString());
                            //JSONObject mObject = new JSONObject(response.toString());
                            JSONArray results = response.getJSONArray("results");
                            if (response.getInt("resultCount")!=0) {
                                for (int i = 0; i < results.length(); i++) {
                                    Song mSong = new Song();
                                    JSONObject explrObject = results.getJSONObject(i);
                                    mSong.setWrapperType(explrObject.optString("longDescription", " "));
                                    mSong.setKind(explrObject.optString("kind", ""));
                                    mSong.setArtistId(explrObject.optString("artistId", " "));
                                    mSong.setCollectionId(explrObject.optString("collectionId", ""));
                                    mSong.setTrackId(explrObject.optString("trackId", ""));
                                    mSong.setArtistName(explrObject.optString("artistName", " "));
                                    mSong.setCollectionName(explrObject.optString("collectionName", ""));
                                    mSong.setTrackName(explrObject.optString("trackName", ""));
                                    mSong.setArtWork(explrObject.optString("artworkUrl100"));
                                    mSong.setPreviewUrl(explrObject.optString("previewUrl",""));

                                    mObjects.add(mSong);


                                }
                                mAdapter = new MyAdapter(DetailSong.this, mObjects);
                                mRV.setAdapter(mAdapter);
                            }else
                            {
                                Toast.makeText(DetailSong.this,"Something went wrong, try again.",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailSong.this, R.string.noMoreSongs, Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void clearData() {
        //http://stackoverflow.com/questions/29978695/remove-all-items-from-recyclerview
        int size = this.mObjects.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mObjects.remove(0);
            }
            //notifyAll();
            mAdapter.notifyItemRangeRemoved(0, size);
        }
    }
}
