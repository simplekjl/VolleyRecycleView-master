package company.example.volleyrecycleview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import company.example.volleyrecycleview.Adapter.MyAdapter;
import company.example.volleyrecycleview.Model.Song;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private List<Song> mList = new ArrayList<>();
    private List<Song> mObjects = new ArrayList<>();
    public RecyclerView mRV;
    public GridLayoutManager mLayoutManager;
    public RecyclerView.Adapter mAdapter;
    private static MainActivity mInstance;
    private static Context mAppContext;
    private ImageLoader mImageLoader;
    private ImageButton btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //variables de entorno
        mInstance = this;
        mTextView = (TextView)findViewById(R.id.txtSearch);
        btnSearch = ( ImageButton) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTextView.getText() != null) {
                    clearData();
                    callServer(mTextView.getText().toString());
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mTextView.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }

            }
        });
        mTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (mTextView.getText() != null) {
                        clearData();
                        callServer(mTextView.getText().toString());
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(mTextView.getWindowToken(),
                                InputMethodManager.RESULT_UNCHANGED_SHOWN);

                    }
                    return true;
                }
                return false;
            }
        });
        /**
         * cachar el evento del icono del boton
         * http://stackoverflow.com/a/26269435
         */
        /*
        //http://stackoverflow.com/questions/13135447/setting-onclicklistner-for-the-drawable-right-of-an-edittext
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (event.getRawX() >= (mTextView.getRight()
                            - mTextView.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        {
                            //http://stackoverflow.com/a/26269435
                            if (mTextView.getText()!=null) {
                                clearData();
                                /
                                  //FUncion que manda a esconder el inchi teclado si gustas revisar here is the SO
                                 // http://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
                                 //
                                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(mTextView.getWindowToken(),
                                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                                // Mandamos a llamar la funcion call erver que obtiene la peticion JSON
                                callServer(mTextView.getText().toString());

                            }
                        }
                        return true;
                    }
                }
                return false;
            }
        });*/
        mRV = (RecyclerView)findViewById(R.id.mRV);
        mRV.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this,1);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRV.setLayoutManager(mLayoutManager);
        //callServer(this);
        //mAdapter = new MyAdapter(mObjects);
        //mRV.setAdapter(mAdapter);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callServer(getApplicationContext());
            }
        });*/
    }

    private void callServer(final String word) {
        // Instantiate the RequestQueue.

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = null;
        try {
            //http://stackoverflow.com/a/10786112
            url = "http://itunes.apple.com/search?term="+ URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest( url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.


                        try {
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
