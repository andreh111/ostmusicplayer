package com.store.anime.ostshow.helpers;

/**
 * Created by Maher on 10/12/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.store.anime.ostshow.models.Song;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mcastra.db";
    public static final int DATABASE_VERSION = 11;
    // define constants for students table
    public static final String SONGS_TABLE_NAME = "songz";
    public static final String SONGS_COLUMN_ID = "songID";
    public static final String SONGS_COLUMN_NAME = "songName";
    public static final String SONGS_COLUMN_SingerName = "singerName";
    public static final String SONGS_COLUMN_AlbumPHOTO = "albumPhoto";
    public static final String SONGS_COLUMN_AlbumName = "albumName";
    public static final String SONGS_COLUMN_SongUrl = "songUrl";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table students
        db.execSQL("create table " + SONGS_TABLE_NAME + " (" +
//                SONGS_COLUMN_ID + " numeric(100) primary key, " +
                SONGS_COLUMN_NAME + " varchar(350) not null, " +
                SONGS_COLUMN_SingerName + " varchar(350) not null, " +
                SONGS_COLUMN_AlbumPHOTO + " varchar(350) not null, " +
                SONGS_COLUMN_SongUrl + " varchar(350) not null, " +
                SONGS_COLUMN_AlbumName + " varchar(350) not null)");

        // insert some initial data into table courses
//        db.execSQL("insert into courses values ('csci200', 'Introduction to Computers')");
//        db.execSQL("insert into courses values ('csci250', 'Introduction to Programming')");
//        db.execSQL("insert into courses values ('csci300', 'Intermediate Programming with Objects')");
//        db.execSQL("insert into courses values ('csci250L', 'Introduction to Programming Lab')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SONGS_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + GRADES_TABLE_NAME);
        onCreate(db);
    }

    public void addSong (Song s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(SONGS_COLUMN_ID, s.getId());
        contentValues.put("songName", s.getName());
        contentValues.put("singerName", s.getSingername());
        contentValues.put("albumPhoto", s.getUrlPhoto());
        contentValues.put("songUrl", s.getUrlSong());
        contentValues.put("albumName", s.getAlbum());
        db.insertWithOnConflict(SONGS_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_ABORT);
        db.close();
    }

    public Song getSong(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from " + SONGS_TABLE_NAME +
                " where " + SONGS_COLUMN_ID + " = " + id , null );
        res.moveToFirst();
        Song s = new Song(res.getString(1), res.getString(2), res.getString(3), res.getString(4),res.getString(5));
        db.close();
        return s;
    }

    public void updateSong (Song s) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SONGS_COLUMN_ID, s.getId());
        contentValues.put(SONGS_COLUMN_NAME, s.getName());
        int count = db.update(SONGS_TABLE_NAME, contentValues,
                SONGS_COLUMN_ID + " = ? ", new String[] { Integer.toString(s.getId()) } );
        db.close();
        if (count < 1) throw new Exception("record not found");
    }

    public void deleteSong (int id) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        int count = db.delete(SONGS_TABLE_NAME,
                SONGS_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
        db.close();
        if (count < 1) throw new Exception("record not found");
    }

    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> array_list = new ArrayList<Song>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select songName,singerName,albumPhoto,songUrl,albumName from " +
                SONGS_TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(new Song(res.getString(0),res.getString(1),res.getString(2),res.getString(3),res.getString(4)));
            res.moveToNext();
        }
        db.close();
        return array_list;
    }

}
