package com.example.clone;


import javafx.collections.FXCollections;
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
import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;

import static com.example.clone.Friend.Perdita;

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
    protected ComboBox<String> FriendsBox=new ComboBox<String>();

    @FXML
    private final ImageView gifView = new ImageView();

    @FXML
    private Label GifLabel;

    @FXML
    private AnchorPane Pane;

    @FXML
    protected Label DateLabel;

    private final Calendar date=new GregorianCalendar(2020,0,1);
    //Friend amico = new Friend();
    Kogo kogo = new Kogo();
    ObservableList<String> names;
    ArrayList<Friend> friendArrayList = new ArrayList<Friend>();
    private final File saves =new File("saves.csv");
    private static final DecimalFormat df = new DecimalFormat("0.00");

    @FXML
    void HangButtonListner(ActionEvent event) {
        //playAndResizeGIF("/gif1.gif",200,500);

        // qua si chiama la funzione per rubare e per farlo devo reperire il ogetto del amico il cui nominativo e preso dal item selezionato nella combo box
        // al momento del click sull pulsasnte
        try {
            Friend selectedFriend = FindAmico(FriendsBox.getSelectionModel().getSelectedItem());
            if (selectedFriend != null) {
                kogo.Rubare(Perdita(selectedFriend));
                BalanceControll(selectedFriend);
            } else {
                System.out.println("Nessun amico trovato per il nome selezionato.");
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        LabelUpdate(balanceLabel,df.format(kogo.getMoney())+"$");
        LabelUpdate(FormLabel, kogo.getForm());

        ChangeData(1);
    }

    @FXML
    void GymButtonListner(ActionEvent event) {
        String giorn=InputDialogGym("Palestra", "Quanti giorni passi in palestra?");
        int g=Integer.parseInt(giorn);
        ChangeData(g);
        kogo.Spendere((float) 5*g);
        LabelUpdate(balanceLabel,df.format(kogo.getMoney())+"$");
    }



    @FXML
    void ShopButtonListener(ActionEvent event) {
        kogo.Spendere((float)50);
        LabelUpdate(balanceLabel, df.format(kogo.getMoney())+"$");

    }

    @FXML
    void SaveButtonListener(ActionEvent event) {

    }

    @FXML
    void LoadBittonListener(ActionEvent event) {

    }

    @FXML
    void FriendsBoxListener(ActionEvent event) throws IOException {
        if (FriendsBox.isDisable()) return; // Se è disabilitato, non fare nulla
        System.out.println("ComboBoxListener attivato! Selezione: " + FriendsBox.getSelectionModel().getSelectedItem());

        if (FriendsBox.getItems().isEmpty()) {
            AddItems(FriendsBox);
            ArrayFriends();
        }

        // Get the selected item
        int i =FriendsBox.getItems().size();
        System.out.println("Size: "+ i);
        System.out.println("Selected :"+FriendsBox.getSelectionModel().getSelectedIndex());
        System.out.println("Amico Sel: " +FriendsBox.getSelectionModel().getSelectedItem());

        //controlla se si seleziona aggiungi e solo in quel caso fa fare il inserimento
        if( FriendsBox.getSelectionModel().getSelectedItem().equals("Aggiungi")) {
            InputDialogFriend("Aggiungi Amico", "Nome Cognome", "Nome: ");
            FriendsBox.setDisable(true); // Disattiva il listener momentaneamente
            AddItems(FriendsBox);
            ArrayFriends();
            FriendsBox.setDisable(false);
            /*System.out.println("Elementi nella ComboBox:");
            for (String item : FriendsBox.getItems()) {
                System.out.println(item);
            }
            */
            //Platform.runLater(() -> FriendsBox.getSelectionModel().clearSelection());
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

    public void ArrayFriends() throws IOException {
        File file = new File("Friends.csv");

        if (!file.exists()) {
            System.out.println("Il file Friends.csv non esiste.");
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        int i = 0;
        friendArrayList.clear();

        while ((line = br.readLine()) != null) {
            i++;
            if (i == 1) continue; // Salta l'intestazione

            String[] s = line.split(",");

            if (s.length < 5) {
                System.out.println("Errore: Riga non valida -> " + line);
                continue;
            }

            try {
                Friend a = new Friend(s[0], s[1], s[2], s[3], s[4]);

                friendArrayList.add(a);
            } catch (NumberFormatException e) {
                System.out.println("Errore nel parsing del saldo: " + line);
            }
        }
        br.close();
        // **Verifichiamo se tutti gli amici sono stati caricati**
        System.out.println("Amici caricati: " + friendArrayList.size());
        for (Friend f : friendArrayList) {
            System.out.println(f.getName() + " " + f.getSurname() + " - Balance: " + f.getBalance());
        }
    }



    public void AddItems(ComboBox<String> cb) {
        try {
            if (names == null) {
                names = FXCollections.observableArrayList();
            }
            names.clear();

            List<String> retrievedNames = Friend.getNames();
            if (retrievedNames != null) {
                names.addAll(retrievedNames);
            }
            names.add("Aggiungi");

            // Salva la selezione attuale
            String selected = cb.getSelectionModel().getSelectedItem();

            cb.getItems().setAll(names); // Usa `setAll` invece di `clear` + `addAll`

            // Ripristina la selezione attuale se ancora presente
            if (names.contains(selected)) {
                cb.getSelectionModel().select(selected);
            } else {
                cb.getSelectionModel().clearSelection();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void ShowInfo(String title, String message){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("This is an information dialog.");
        alert.setContentText(message);
        alert.showAndWait();
        FriendsBox.getSelectionModel().select(0);
    }

    public void InputDialogFriend(String title, String description,String name) throws IOException {

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
            friendArrayList.add(a);
            String message =a.getName()+" "+a.getSurname()+"\n E interessato a: " + a.getHobby();
            ShowInfo("Amico", message);

        }
        System.out.println("salvo Input");
    }

    private String InputDialogGym(String title, String description) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(description);
        dialog.setContentText("Days:");
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
            return result.get();
        else
            return "";
    }

    // Search for the friend in the combo box and return the object from the array
    public Friend FindAmico(String nomeCompleto) {
        for (Friend a : friendArrayList) {
            String fullName = a.getName() + " " + a.getSurname();
            System.out.println();
            a.ToStampa();
            if (fullName.equals(nomeCompleto)) {
                return a;
            }
        }
        return null; // Se non lo trova
    }

    //Controls the balance of the friend and does the delete or the update of the friend on file
    public void BalanceControll(Friend a) throws IOException {
        if(a.getBalance()>0)
            a.Update(a);
        else{
            friendArrayList.remove(a);
            a.DeleteFromFile(a);
            AddItems(FriendsBox);
        }
    }

    }
