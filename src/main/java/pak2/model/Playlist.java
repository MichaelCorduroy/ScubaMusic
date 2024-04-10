// Represents a playlist containing a collection of songs.
package pak2.model;

import java.util.ArrayList;
import java.util.List;

//this is a comment
public class Playlist {
    private String name;
    private List<Song> songs;

    // Constructor
    public Playlist (String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    // Method to add a song to the playlist
    public void addSong(Song song) {
        songs.add(song);
    }

    // Method to delete a song to the playlist
    public void removeSong(Song song) {
        songs.remove(song);
    }

}
