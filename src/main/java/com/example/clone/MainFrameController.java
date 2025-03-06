package com.example.clone;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

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
    protected ComboBox<String> FriendsBox=new ComboBox<>();

    @FXML
    private ImageView gifView = new ImageView();

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
    void FriendsBoxListener(ActionEvent event) throws IOException {

        if(FriendsBox.getItems().isEmpty()){
            try {
                AddItems(FriendsBox);
                ArrayAmici();
                FriendsBox.getSelectionModel().select(1);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        // Get the selected item

        int i =FriendsBox.getItems().size();
        System.out.println("Size: "+ i);
        System.out.println("Selected ;"+FriendsBox.getSelectionModel().getSelectedIndex());
        System.out.println("Amico Sel: " +FriendsBox.getSelectionModel().getSelectedItem()); //

        //controlla se si seleziona aggiungi e solo in quel caso fa fare il inserimento
        if( FriendsBox.getSelectionModel().getSelectedIndex() == i-1) {
            InputDialog("Aggiungi Amico", "Nome Cognome", "Nome: ");
            FriendsBox.getItems().clear();
            AddItems(FriendsBox);
            ArrayAmici();
        }
    }

    public void LabelUpdate(Label label,String s){

        // Update the label's text
        label.setText(s);

        // Trigger a layout pass to refresh the label
        label.applyCss(); // Forces a refresh of CSS
        label.layout();
    }

    public void ChangeData(int n){
        date.add(Calendar.DAY_OF_MONTH, n);
        int month =date.get(Calendar.MONTH)+1;
        String dateStr = date.get(Calendar.DAY_OF_MONTH) + "/" + month + "/" + date.get(Calendar.YEAR);
        LabelUpdate(DateLabel,dateStr);
    }

    public Image resizeImage(Image originalImage, double width, double height) {
        return new Image(originalImage.getUrl(), width, height, true, true);
    }

    public void playAndResizeGIF(String path, double width, double height) {
        // Use URL to load the original image
        URL imageUrl = getClass().getResource(path);
        if (imageUrl != null) {
            Image originalImage = new Image(imageUrl.toString());

            // Resize the image
            Image resizedImage = resizeImage(originalImage, width, height);

            // Set the resized image to the ImageView
            gifView.setImage(resizedImage);
            gifView.setVisible(true);
        } else {
            System.out.println("Image not found: " + path);
        }
    }

    public void ArrayAmici() throws IOException{
        File file = new File("Friends.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int i=0;
        BigDecimal n;
        amici.clear();
        while ((line = br.readLine()) != null) {
            i++;
            if(i!=1){
                String s[]= line.split("\t\t");
                Friend a = new Friend(s[0],s[1]);
                a.setSex(s[2]);
                a.setHobby(s[3]);
                n= new BigDecimal(Float.parseFloat(s[4].replace(",",".")));
                //n.setScale(2, RoundingMode.HALF_UP);
                //System.out.println(n.floatValue());
                a.setBalance(n.floatValue());

                amici.add(a);
            }

        }
    }

    public void AddItems(ComboBox<String> cb) throws IOException{

        //names = amico.getNames();
        // names.add("Aggiungi");

        names.clear();
        names = amico.getNames();
        names.add("Aggiungi");

        cb.getItems().clear();
        cb.getItems().addAll(names);


    }

    public void ShowInfo(String title, String message){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("This is an information dialog.");
        alert.setContentText(message);
        alert.showAndWait();

    }

    public void InputDialog(String title, String description,String name){

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(description);
        dialog.setContentText(name);

        Optional<String> result = dialog.showAndWait();

        String Name,Surname,str;
        if(result.isPresent()){
            str = result.get();
            int space1 = str.indexOf(" ");
            Name=str.substring(0, space1);
            Surname=str.substring(space1).trim();
            Friend a = new Friend(Name,Surname);
            a.AddToFile(a,true);
            String message =a.getName()+" "+a.getSurname()+"\n E interessato a: " + a.getHobby();
            ShowInfo("Amico", message);

        }

    }

}
