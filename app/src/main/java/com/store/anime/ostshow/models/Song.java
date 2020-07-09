package com.store.anime.ostshow.models;

/**
 * Created by Anonym on 10/7/2017.
 */

public class Song {
    private int id;
    private String name;
    private String singername;
    private String urlPhoto;
    private String urlSong;
    private String album;

    public Song(int id,String name, String singername, String urlPhoto, String urlSong,String album) {
        this.id = id;
        this.name = name;
        this.singername = singername;
        this.urlPhoto = urlPhoto;
        this.urlSong = urlSong;
        this.album = album;
    }
    public Song(String name, String singername, String urlPhoto, String urlSong,String album) {
        this.name = name;
        this.singername = singername;
        this.urlPhoto = urlPhoto;
        this.urlSong = urlSong;
        this.album = album;
    }
    public Song(String name,String albumname,String urlPhoto){
        this.name = name;
        this.album = albumname;
        this.urlPhoto = urlPhoto;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getUrlSong() {
        return urlSong;
    }

    public void setUrlSong(String urlSong) {
        this.urlSong = urlSong;
    }
}
