package com2.model;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import static org.junit.jupiter.api.Assertions.*;
class SongClassTest {

   /*This test function tests if the Song class can properly
   parse details from song metadata in local song files
   */



    //checks local files for music

     private File[] files;
     private File directory;
    private  ArrayList<Song> songs;


    public void initialize(URL arg0, ResourceBundle arg1) {

        files = directory.listFiles((file) -> !file.getName().startsWith("."));
        directory = new File("src/main/java/com2/audio/localmusicfiles/");
        songs = new ArrayList<Song>();



        if (files != null) {
            for (File file : files) {
                System.out.println(file);

                // Assuming a delimiter of "_" and filename format like "title_artist_album.wav"
                String[] data = file.getName().split("&");

                // Extracting information based on your filename format
                String title = data[0];
                String artist = data[1];
                String album = data[2].split("\\.")[0];  // Remove extension
                String genre = "Unknown";  // If genre is not included in filename

                // Create Song object with parsed data
                Song newSong = new Song(title, artist, genre, album);
                songs.add(newSong);


            }
        }
        else{
            System.out.println("files is null");
        }

    }

    @Test
    void getTitle() {


        if (files != null) {
            for (File file : files) {
                System.out.println("hi");

                // Assuming a delimiter of "_" and filename format like "title_artist_album.wav"
                String[] data = file.getName().split("&");

                // Extracting information based on your filename format
                String title = data[0];
                String artist = data[1];
                String album = data[2].split("\\.")[0];  // Remove extension
                String genre = "Unknown";  // If genre is not included in filename

                // Create Song object with parsed data
                Song newSong = new Song(title, artist, genre, album);
                songs.add(newSong);


            }
        }
        assertTrue((files != null));
        assertEquals("enough_(feat. cranes)", songs.get(0).getTitle());
    }

    @Test
    void getArtist() {
        assertEquals("enough_(feat. cranes)", songs.get(0).getArtist());
    }

    @Test
    void getGenre() {
    }

    @Test
    void getAlbum() {
        assertEquals("enough_(feat. cranes)", songs.get(0).getAlbum());
    }

    @Test
    void getFilePath() {
        //TODO
    }
}