package company.example.volleyrecycleview.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * SOurces https://dzone.com/articles/using-android-parcel
 * Created by admin on 1/27/2016.
 */
public class Song implements Parcelable {

    private String wrapperType;
    private String kind;
    private String artistId;
    private String collectionId;
    private String trackId;
    private String artistName;
    private String collectionName;
    private String trackName;
    private String artWork;

    public Song() {
    }

    public Song(String wrapperType, String kind, String artistId, String collectionId, String trackId, String artistName, String collectionName, String trackName, String artWork) {
        this.wrapperType = wrapperType;
        this.kind = kind;
        this.artistId = artistId;
        this.collectionId = collectionId;
        this.trackId = trackId;
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.trackName = trackName;
        this.artWork = artWork;
    }

    @Override
    public String toString() {
        return "Song{" +
                "wrapperType='" + wrapperType + '\'' +
                ", kind='" + kind + '\'' +
                ", artistId=" + artistId +
                ", collectionId=" + collectionId +
                ", trackId=" + trackId +
                ", artistName='" + artistName + '\'' +
                ", collectionName='" + collectionName + '\'' +
                ", trackName='" + trackName + '\'' +
                ", artWork='" + artWork + '\'' +
                '}';
    }

    public String getArtWork() {
        return artWork;
    }

    public void setArtWork(String artWork) {
        this.artWork = artWork;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }


    /**
     * Implementing Parceable to the pojo
     *
     * @return
     */
    private Song(Parcel in) {
        wrapperType     = in.readString();
        kind            = in.readString();
        artistId        = in.readString();
        collectionId    = in.readString();
        trackId         = in.readString();
        artistName      = in.readString();
        collectionName  = in.readString();
        trackName       = in.readString();
        artWork         = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(wrapperType);
        dest.writeString(kind);
        dest.writeString(artistId);
        dest.writeString(collectionId);
        dest.writeString(trackId);
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeString(trackName);
        dest.writeString(artWork);

    }

    //Variables del parceable
    public static final Parcelable.Creator<Song> CREATOR = new ClassLoaderCreator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[0];
        }

        @Override
        public Song createFromParcel(Parcel source, ClassLoader loader) {
            return new Song(source);
        }
    };

}
