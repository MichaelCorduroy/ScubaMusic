package com.example.scubamusic0_0_1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;

//import java.sql;





import java.io.IOException;
import java.net.URL;
import java.util.Objects;










public class ScubaMusic extends Application {

    public boolean paused = false;




    //Image icon = new Image(Objects.requireNonNull(getClass().getResource("src/main/resources/graphics/scubaLogo.png")).toString());
    //Tooltip tooltip = new Tooltip("ScubaMusic");


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ScubaMusic.class.getResource("scbmwelcome.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("ScubaMusic v. 0.0.1");
        //stage.getIcons().add(new Image("scubaLogo.png"));
        //stage.setTooltip(tooltip);
        stage.setScene(scene);
	stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
