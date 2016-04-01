package company.example.volleyrecycleview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import company.example.volleyrecycleview.Model.Song;
import company.example.volleyrecycleview.Model.VolleySingleton;
import company.example.volleyrecycleview.R;

/**
 * Created by admin on 1/27/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static Context context;
    private List<Song> dataCollection;
    private ImageLoader mImageLoader;

    public MyAdapter(Context activity, List<Song> dataSet) {
        this.dataCollection = dataSet;
        context = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setClickable(true);
        ViewHolder vh = new ViewHolder(v,dataCollection);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Song current = dataCollection.get(position);
        holder.setItem(current);
    }

    @Override
    public int getItemCount() {
        return dataCollection.size();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        private TextView mTitleSong;
        private TextView mArtist;
        private TextView mArtistId;
        private TextView mCollectionId;
        private NetworkImageView mNetworkImageView;
        private ImageView imageView;
        private ImageLoader mImageLoader;
        private Button btn;

        public ViewHolder(View v, final List<Song> mList) {
            super(v);

            mTitleSong   = (TextView) v.findViewById(R.id.titleSong);
            mArtist      = (TextView) v.findViewById(R.id.artist);
            imageView = (ImageView) v.findViewById(R.id.IMG);
            btn          = (Button) v.findViewById(R.id.button);
            mImageLoader = VolleySingleton.getInstance(v.getContext()).getImageLoader();
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Toast.makeText(context, mList.get(pos).getArtistName(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public void setItem(Song item) {

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if (item.getTrackName() != null)
                mTitleSong.setText(item.getTrackName());

            if (item.getArtistName() != null)
                mArtist.setText(item.getArtistName());

            if (item.getArtistId() != null)
                mArtistId.setText(item.getArtistId().toString());

            if (item.getCollectionId() != null)
                mCollectionId.setText(item.getCollectionId().toString());

            if(item.getArtWork()!= null){
                String url = item.getArtWork().substring(0,item.getArtWork().length()- 13);

                Picasso.with(context).load(url+"500x500bb.jpg")
                        .placeholder(R.mipmap.default_placeholder)
                        .error(R.mipmap.default_placeholder)

                        .into(imageView);

                //mNetworkImageView.setImageUrl(item.getArtWork(),mImageLoader);

            }

        }
    }

}


