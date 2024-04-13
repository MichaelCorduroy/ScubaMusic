package com.example.scubamusic0_0_1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import pak2.model.Song;
import pak2.model.Playlist;



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
    public ImageView playImage;
    public Button replayButton;
    public ImageView replayImg;
    public TextField searchBar;
    public ListView<String> resultsList;
    public MenuButton activePlaylist;


    private ArrayList<File> songs;

    private ArrayList<String> mix;
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

    private boolean replaying = false;

    ArrayList<String> allSearchResults = new ArrayList<>();





    InputStream stream = new FileInputStream("src/main/resources/icons/pause.png");
    Image pauseImage = new Image(stream);
    InputStream steam = new FileInputStream("src/main/resources/icons/play.png");
    Image plyImage = new Image(steam);

    //Image pauseImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/main/resources/icons/pause.png")));
    //Image plyImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("src/main/resources/icons/play.png")));
    Button btn = new Button();

    public MusicController() throws FileNotFoundException {
    }


    private final ObservableList<String> filteredSongs = FXCollections.observableArrayList();


    //this function initializes the music controller
    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        songs = new ArrayList<File>();
        mix = new ArrayList<String>();

        // Dropdown menu
        resultsList.getSelectionModel().selectedItemProperty().addListener(

                (observable, oldValue, newValue) -> {

                    int selectedIndex = resultsList.getSelectionModel().getSelectedIndex();
                    if (newValue != null && !allSearchResults.isEmpty()) {
                        // Do something with the selected item title (e.g., play the song)
                        // Check if selected index is valid (avoid out-of-bounds)
                        if (selectedIndex > 0) {
                            // Move the selected item to index 0 in allSearchResults
                            String selectedItem = allSearchResults.get(selectedIndex);
                            allSearchResults.remove(selectedIndex);
                            allSearchResults.add(0, selectedItem);
                        }
                        System.out.println(newValue);
                        playSong(allSearchResults);
                    }
                    else{
                        System.out.println("null!");
                    }
                }
        );


        //initializes initial playlist
        playList = new Playlist("MainMix");

        //checks local files for music
        directory = new File("src/main/java/pak2/audio/localmusicfiles/");
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

        for (File song : songs) {
                String baseName = "";
                int extensionIndex = song.getName().lastIndexOf('.'); // Find the last dot

                if (extensionIndex > 0) {
                    baseName = song.getName().substring(0, extensionIndex);  // Extract up to the dot
                }
                mix.add(baseName);
        }
        System.out.println(songs);


            for (String song : mix) {
                MenuItem menuItem = new MenuItem(song);
                activePlaylist.getItems().add(menuItem);
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
        playButton.setBackground(null);
        prevButton.setBackground(null);
        nextButton.setBackground(null);
        replayButton.setBackground(null);

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
        playImage.setImage(pauseImage);
        //System.out.println("MediaPlayer State: " + mediaPlayer.getStatus());
        //playImage.setImage(plyImage);
    }


    //this method handles pausing song media
    public void pauseMedia(){
        cancelTimer();
        mediaPlayer.pause();
        playImage.setImage(plyImage);
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
                    nextMedia();
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
            playing = true;
        }
        else{
            pauseMedia();
            playing = false;
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
            if(!replaying) {
                songIndex++;
            }
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
            if(!replaying) {
                songIndex = 0;
            }
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

    public void replay(ActionEvent actionEvent) {
        if(!replaying){
            replaying = true;
            replayImg.setOpacity(1);
        }
        else {
            replaying = false;
            replayImg.setOpacity(0.16);
        }
    }

    public void searching(KeyEvent keyEvent) {

        String searchTerm = searchBar.getText().toLowerCase();

        if(!searchTerm.isEmpty()) {
            filteredSongs.clear();
            allSearchResults.clear();
            for (File song : songs) {
                if (song.getName().toLowerCase().contains(searchTerm)) {
                    String baseName = "";
                    int extensionIndex = song.getName().lastIndexOf('.'); // Find the last dot

                    if (extensionIndex > 0) {
                        baseName = song.getName().substring(0, extensionIndex);  // Extract up to the dot
                    }
                    filteredSongs.add(baseName);
                }
            }
            if(!filteredSongs.isEmpty()) {
                resultsList.setItems(filteredSongs);
                allSearchResults.addAll(filteredSongs);
                System.out.println(allSearchResults.toString());
            }
            else{
                filteredSongs.add("No results found :(");
                resultsList.setItems(filteredSongs);

            }
            resultsList.setVisible(true);
        }
        else{
            resultsList.setVisible(false);
        }
    }


    private void playSong(ArrayList<String> searchResults) {
        // Construct the path to the song file based on the song title (replace with your logic)

        songs.clear();
        for(String result: searchResults) {
            String songPath = result + ".wav";
            directory = new File("src/main/java/pak2/audio/localmusicfiles/" + songPath);
            songs.add(directory);
        }
        try {
            songIndex = 0;
            media = new Media(songs.get(songIndex).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            playMedia();
            mediaPlayer.play();
            songLabel.setText(songs.get(songIndex).getName());
        } catch (Exception e){
            // Handle potential exceptions during playback
            System.out.println(e);
        }
    }



    public void selectSong(MouseEvent mouseEvent) {
            songs.clear();
            directory = new File("src/main/java/pak2/audio/localmusicfiles/");
            files = directory.listFiles((file) -> !file.getName().startsWith("."));

            //System.out.println(directory.isDirectory());

            if (files != null) {
                //songs.addAll(Arrays.asList(files));
                for (File file : files) {
                    System.out.println(file);
                    Song newSong = new Song("title", "artist", "genre", "album");
                    songs.add(file);
                }
            }
        }

}

