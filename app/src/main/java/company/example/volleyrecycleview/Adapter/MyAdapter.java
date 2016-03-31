package company.example.volleyrecycleview.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.List;

import company.example.volleyrecycleview.Model.Song;
import company.example.volleyrecycleview.R;

/**
 * Created by admin on 1/27/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Song> mList;

    public MyAdapter() {
        //nadda
    }

    public MyAdapter(List<Song> mList) {
        this.mList = mList;
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final int position = viewType;
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        v.setClickable(true);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mWrapperType.setText(mList.get(position).getWrapperType().toString());
        holder.mKind.setText(mList.get(position).getKind().toString());
        holder.mArtistId.setText(mList.get(position).getArtistId().toString());
        holder.mCollectionId.setText(mList.get(position).getCollectionId().toString());
        holder.mTrackId.setText(mList.get(position).getTrackId().toString());
        holder.mArtistId.setText(mList.get(position).getArtistName().toString());
        holder.mCollectionName.setText(mList.get(position).getCollectionName().toString());

        try{
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(holder.itemView);
        }
        catch(Exception e){}

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            v.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), String.valueOf(getPosition()), Toast.LENGTH_LONG).show();
        }

    }
}
