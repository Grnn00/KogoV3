package com.example.clone;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainFrameController {

    @FXML
    private Button LoadButton;

    @FXML
    private Label imageLabel;

    @FXML
    private Button ShopButton;

    @FXML
    private Button GymButton;

    @FXML
    private Button hang_outButton;

    @FXML
    private Button SaveButton;

    @FXML
    private Label balanceLabel;

    @FXML
    private Label FormLabel;

    @FXML
    private ComboBox<String> FriendsBox=new ComboBox<>();

    @FXML
    private Label GifLabel;

    @FXML
    private AnchorPane Pane;

    @FXML
    private Label DateLabel;

    private Calendar date;
    Friend amico = new Friend();
    Kogo kogo = new Kogo();
    ObservableList<String> names;
    ArrayList<Friend> amici = new ArrayList<Friend>();
    private File saves =new File("saves.csv");
    private static final DecimalFormat df = new DecimalFormat("0.00");


    @FXML
    void HangButtonListner(ActionEvent event) {

    }

    @FXML
    void GymButtonListner(ActionEvent event) {

    }

    @FXML
    void ShopButtonListener(ActionEvent event) {

    }

    @FXML
    void SaveButtonListener(ActionEvent event) {

    }

    @FXML
    void LoadBittonListener(ActionEvent event) {

    }

    @FXML
    void FriendsBoxListener(ActionEvent event) {

    }

}
