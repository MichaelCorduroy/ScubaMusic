module com.example.scubamusic0_0_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.scubamusic0_0_1 to javafx.fxml;
    exports com.example.scubamusic0_0_1;
}