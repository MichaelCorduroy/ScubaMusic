package com2.audio;

import javafx.fxml.Initializable;

import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class MusicPlayerController implements Initializable {

    /*
        this code is to test music functionality

        the code below is subject to change as the 'song' and 'playlist'
        classes are implemented
     */
    private File directory;
    private File[] files;
    private static ArrayList<File> songs;
    private static int songIndex;
    private Timer timer;
    private static Media media;
    private static MediaPlayer mediaPlayer;
    private TimerTask task;
    private boolean playing;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){

        songs = new ArrayList<File>();
        directory = new File("/localmusicfiles");
        files = directory.listFiles();

        if(files != null){

            //songs.addAll(Arrays.asList(files));
            for(File file : files) {
                System.out.println(file);
                songs.add(file);
            }
        }

        media = new Media(songs.get(songIndex).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

       //TODO: change song label with song change
        // songLabel.setText(songs.get(songNumber).getName());

    }

    public static void playMedia(){
        mediaPlayer.play();
        System.out.println("yo! lets play!");
    }

    public void pauseMedia(){
        mediaPlayer.pause();
    }

    public void resetMedia(){
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void prevMedia(){

    }

    //this method handles when the user presses the 'next' button
    public static void nextMedia(){

        if(songIndex < songs.size() - 1){

            //stops the current track and changes index to next track
            mediaPlayer.stop();
            songIndex++;

            //loads the new song for playing
            media = new Media(songs.get(songIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            //TODO: Change tne song label to the next song after skipping
            //play the next song
            mediaPlayer.play();
        }
        //returns to the beginning of the playlist when the
        else{

            //stops the current track then jumps to the first track
            mediaPlayer.stop();
            songIndex = 0;

            //loads the new song for playing
            media = new Media(songs.get(songIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
        }

    }

}
