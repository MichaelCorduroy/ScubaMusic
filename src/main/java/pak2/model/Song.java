package pak2.model;
// Represents a song with attributes like the title, artist, genre, etc.

import java.util.Objects;

// This is a comment
public class Song {
    private String title;
    private String artist;
    private String genre;
    private String album;
    private String filePath;

    //Constructor
    public Song(String title, String artist, String genre, String album) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.album = album;
        this.filePath = filePath;
    }

    // Getter Methods
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbum() {
        return album;
    }

    public String getFilePath() {
        return filePath;
    }

    // Helper methods
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Song song = (Song) obj;
        return Objects.equals(title, song.title) && Objects.equals(artist, song.artist);
    }

    public int hashCode() {
        return Objects.hash(title, artist);
    }

    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", album='" + album + '\'' +
                '}';
    }

    public void saveSongForUser(String username) {
        System.out.println("Song '" + title + "' saved for user '" + username + "'.");
    }
}
