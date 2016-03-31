package company.example.volleyrecycleview.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import company.example.volleyrecycleview.Model.Song;
import company.example.volleyrecycleview.R;

/**
 * Created by admin on 1/27/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static Context context;
    private List<Song> dataCollection;

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
        ViewHolder vh = new ViewHolder(v);
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
        private TextView mMData;
        private TextView mWrapperType;
        private TextView mKind;
        private TextView mArtistId;
        private TextView mCollectionId;
        private TextView mTrackId;
        private TextView mArtistName;
        private TextView mCollectionName;
        private Button btn;

        public ViewHolder(View v) {
            super(v);

            mMData       = (TextView) v.findViewById(R.id.mData);
            mWrapperType = (TextView) v.findViewById(R.id.wraper);
            mKind        = (TextView) v.findViewById(R.id.kind);
            mArtistId    = (TextView) v.findViewById(R.id.artist);
            mCollectionId= (TextView) v.findViewById(R.id.collection);
            mTrackId     = (TextView) v.findViewById(R.id.trackId);
            mArtistName  = (TextView) v.findViewById(R.id.artistName);
            mCollectionName = ( TextView) v.findViewById(R.id.collectionName);
            btn          = (Button) v.findViewById(R.id.button);

        }

        public void setItem(Song item) {

            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if (item.getWrapperType() != null)
                mWrapperType.setText(item.getWrapperType());

            if (item.getKind() != null)
                mKind.setText(item.getKind());

            if (item.getArtistId() != null)
                mArtistId.setText(item.getArtistId().toString());

            if (item.getCollectionId() != null)
                mCollectionId.setText(item.getCollectionId().toString());

            if (item.getTrackId() != null)
                mTrackId.setText(item.getTrackId().toString());

            if (item.getArtistName() != null)
                mArtistId.setText(item.getArtistName());

            if (item.getCollectionName() != null)
                mCollectionName.setText(item.getCollectionName());
        }
    }

}


