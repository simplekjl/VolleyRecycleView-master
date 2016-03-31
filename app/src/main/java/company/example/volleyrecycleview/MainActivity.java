package company.example.volleyrecycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import company.example.volleyrecycleview.Adapter.MyAdapter;
import company.example.volleyrecycleview.Model.Song;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private List<Song> mList = new ArrayList<>();
    private List<Song> mObjects = new ArrayList<>();
    public RecyclerView mRV;
    public LinearLayoutManager mLayoutManager;
    public RecyclerView.Adapter mAdapter;
    private static MainActivity mInstance;
    private static Context mAppContext;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //variables de entorno
        mInstance = this;


        mTextView = (TextView)findViewById(R.id.txt1);
        mRV = (RecyclerView)findViewById(R.id.mRV);
        mRV.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRV.setLayoutManager(mLayoutManager);
        callServer(this);
        //mAdapter = new MyAdapter(mObjects);
        //mRV.setAdapter(mAdapter);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServer(getApplicationContext());
            }
        });
    }

    private void callServer(final Context mContext) {
        // Instantiate the RequestQueue.

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://itunes.apple.com/search?term=rock";

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest( url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.


                        try {
//                            JSONObject mObject = new JSONObject(response.toString());
//                            JSONArray results = mObject.getJSONArray("results");
//
//                            for( int i= 0; i< results.length();i++){
//                                JSONObject c = results.getJSONObject(i);
//
//                                JSONArray mDetails = c.getJSONArray("results");
//                                for(int j=0;j <mDetails.length();j++){
//                                    Song mSong = new Song();
//                                    JSONObject explrObject = mDetails.getJSONObject(i);
//                                    mSong.setArtistId(explrObject.getInt("artistId"));
//                                    mSong.setArtistName(explrObject.getString("artistName"));
//                                    mSong.setCollectionId(explrObject.getInt("collectionId"));
//                                    mSong.setArtistName(explrObject.getString("artistName"));
//                                    mSong.setKind(explrObject.getString("kind"));
//                                    mSong.setCollectionName(explrObject.getString("collectionName"));
//                                    mSong.setTrackId(explrObject.getInt("trackId"));
//                                    mObjects.add(mSong);
//                                }
//                            }
//                            mAdapter = new MyAdapter(mObjects);
//                            mRV.setAdapter(mAdapter);
                            Log.d("MAINACTIVITY",response.toString());
                            //JSONObject mObject = new JSONObject(response.toString());
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                Song mSong = new Song();
                                JSONObject explrObject = results.getJSONObject(i);
                                mSong.setArtistId(Integer.getInteger(explrObject.optString("artistId")));
                                mSong.setArtistName(explrObject.optString("artistName"));
                                mSong.setCollectionId(Integer.getInteger(explrObject.optString("collectionId")));
                                mSong.setTrackName(explrObject.optString("trackName"));
                                mSong.setKind(explrObject.optString("kind"));
                                mSong.setCollectionName(explrObject.optString("collectionName"));
                                mSong.setTrackId(Integer.getInteger(explrObject.optString("trackId")));
                                mSong.setArtWork(explrObject.optString("artworkUrl100"));

                                mObjects.add(mSong);


                            }
                            mAdapter = new MyAdapter(MainActivity.this, mObjects);
                            mRV.setAdapter(mAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("There is no response ");
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
