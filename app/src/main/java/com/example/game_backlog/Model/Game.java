package com.example.game_backlog.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
/**
 * the entity for the table named "gameTable"
 * assigns column names for both the id and name variables
 * The entity consists of an id, title, platform, status and date
 * **/
@Entity(tableName = "gameTable")
/**implement parcelable which generates required methods**/
public class Game implements Parcelable {

    /**configured to be the primary key and auto-generated**/
    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "platform")
    private String platform;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "date")
    private String date;

    /**normal actions performed by class, since this is still a normal object**/
    public Game(){

    }

    /**using the 'in' variable, the values that have been put into the 'Parcel can be retrieved'**/
    protected Game(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        title = in.readString();
        platform = in.readString();
        status = in.readString();
        date = in.readString();
    }

    /**Matching getter and setter methods**/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    /**After implementing the 'Parcelable' interface,
     * the CREATOR constant is created specified by this class**/
    public static final Creator<Game> CREATOR = new Creator<Game>() {

        /**This calls new constructor and then returns new object**/
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        /**match the size of the class**/
        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**This were the values are written to save to the 'Parcel'**/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(title);
        dest.writeString(platform);
        dest.writeString(status);
        dest.writeString(date);
    }
}
