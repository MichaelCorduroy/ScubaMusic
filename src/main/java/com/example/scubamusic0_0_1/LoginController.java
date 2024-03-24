package com.example.scubamusic0_0_1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class LoginController {


    private Stage stage;
    private Scene scene;
    private Parent root;

    //initialize welcome page elements
    @FXML
    private Button testButton;
    @FXML
    private Button loginButton;
    @FXML
    private Text newUser;
    @FXML
    private Text scoreLabel;




    //this code runs when clicking the 'new user' text
    public void newUserPage(ActionEvent  event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scbmsignup.fxml")));
        stage  = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    //this method handles the click event of the login button
    @FXML
    protected void onLoginClick(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scbmhomemc.fxml")));
        stage  = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
