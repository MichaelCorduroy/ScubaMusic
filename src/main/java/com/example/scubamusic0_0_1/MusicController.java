package com.example.scubamusic0_0_1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.net.URL;
import java.util.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import javafx.event.EventHandler;

import com2.model.Song;
import com2.model.Playlist;



public class MusicController implements Initializable{

    //instantiates references to Music Controller elements
    public ImageView volumeController;
    public Slider volumeSlider;
    public Button nextButton;
    public Button prevButton;
    public HBox mainControls;
    public ProgressBar songProgressBar;
    public Label songLabel;

    public Playlist playList;

    private ArrayList<File> songs;
    private File directory;
    private File[] files;
    private Media media;
    private MediaPlayer mediaPlayer;
    private int songIndex;
    private Timer timer;
    private TimerTask task;





    @FXML
    private Button playButton;


    //tracks music controller play state
    public boolean playing = false;
    private boolean running = false;


    //this function initializes the music controller
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        songs = new ArrayList<File>();

        //initializes initial playlist
        playList = new Playlist("MainMix");

        //checks local files for music
        directory = new File("src/main/java/com2/audio/localmusicfiles/");
        files = directory.listFiles((file) -> !file.getName().startsWith("."));

        //System.out.println(directory.isDirectory());

        if(files != null){
            //songs.addAll(Arrays.asList(files));
            for(File file : files) {
                System.out.println(file);
                Song newSong = new Song("title", "artist", "genre","album");
                songs.add(file);
            }
        }
        else{
            System.out.println(directory.getAbsolutePath());
            System.out.println("No files found in directory.");
        }

        //loads up a new song
        media = new Media(songs.get(songIndex).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songIndex).getName());

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    //this method handles playing song media
    public void playMedia(){
        //System.out.println(songs.get(songIndex).toURI().toString());
        //System.out.println("Media Player Status: " + mediaPlayer.getStatus());
        beginTimer();
        mediaPlayer.setOnError(() -> {
            mediaPlayer.play();
            System.out.println("Error: " + mediaPlayer.getError());
        });

        mediaPlayer.play();
        playButton.setText("Pause");
        //System.out.println("MediaPlayer State: " + mediaPlayer.getStatus());

    }


    //this method handles pausing song media
    public void pauseMedia(){
        cancelTimer();
        mediaPlayer.pause();
    }

    //this method handles temporal features including progress bar
    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask(){
            public void run(){
                running = true;
                double currentTime = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(currentTime/end);

                if(currentTime/end == 1){
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 500, 500);
    }

    public void cancelTimer(){
            running = false;
            timer.cancel();
    }



    //this method handles when the user attempts to change volume
    public void beginVolumeEvent(){
        List<Node> elements = new ArrayList<>();
        elements.add(mainControls);
        // Shifts other elements for volume
        for (Node element : elements) {
            element.setTranslateX(-25);
        }
        //shows volume slider
        volumeSlider.setVisible(true);
    }

    public void endVolumeEvent(){
        List<Node> elements = new ArrayList<>();
        elements.add(mainControls);
        // Shifts other elements back
        for (Node element : elements) {
            element.setTranslateX(0);
        }
        //hides volume slider
        volumeSlider.setVisible(false);
    }



    public void volumeChange(MouseEvent mouseDragEvent) {
       beginVolumeEvent();
    }

    public void volumeLeft(MouseEvent mouseEvent) {
        endVolumeEvent();
    }

    public void volumeChanging(MouseEvent mouseEvent) {
        beginVolumeEvent();
    }

    public void volumeLeaving(MouseEvent mouseEvent) {
        endVolumeEvent();
    }

    //this method handles when the user clicks the play/pause button
    public void playChange(ActionEvent event) {
        if(!playing){
            playMedia();
            System.out.println("playing");
            playing = true;
            playButton.setText("Pause");
        }
        else{
            pauseMedia();
            System.out.println("paused");
            playing = false;
            playButton.setText("Play");
        }
    }

    //this method handles resetting the song
    public void resetMedia(){
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }

    //this method handles when the user clicks the "back" button
    public void prevMedia(){
        double current = mediaPlayer.getCurrentTime().toSeconds();
        if(current <= 3){
            //if the song has been playing < 3 seconds go prev song
            if(songIndex > 0){
                songIndex--;
                mediaPlayer.stop();
                if(running){
                    cancelTimer();
                }

                //loads up a new song
                media = new Media(songs.get(songIndex).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                songLabel.setText(songs.get(songIndex).getName());
                playMedia();
            }
            //if prev on the first song replay the song
            else{
                resetMedia();
                if(running){
                    cancelTimer();
                }
            }


        }
        //otherwise reset the song
        else{
            resetMedia();
            if(running){
                cancelTimer();
            }
        }
    }

    //this method handles when the user clicks the "next" button
    public void nextMedia(){
        //checks playlist bounds
        if(songIndex < songs.size() - 1){
            songIndex++;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }

            //loads up a new song
            media = new Media(songs.get(songIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songIndex).getName());
            playMedia();
        }
        //if skipping the last song return to beginning of playlist
        else{
            songIndex = 0;
            mediaPlayer.stop();
            if(running){
                cancelTimer();
            }

            //loads up a new song
            media = new Media(songs.get(songIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songIndex).getName());
            playMedia();
        }
    }

    public void speedScrubbing(MouseEvent mouseEvent) {

        double clickPosition = mouseEvent.getX();
        System.out.println(clickPosition);
        double mediaDuration = mediaPlayer.getMedia().getDuration().toMillis();
        double newPosition = clickPosition / songProgressBar.getWidth() * mediaDuration;
        mediaPlayer.seek(Duration.millis(newPosition));


    }
}
