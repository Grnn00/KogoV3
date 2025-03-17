package com.example.clone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;

public class Friend {
    private String Name,Surname,hobby;
    private float balance;
    private BigDecimal n;   //variabile per formattare il valore in "balance"
    private String sex;      String[] s={"M","F"};
    static Random rand = new Random();
    static File file = new File("Friends.csv");

    String[] hobbies = {
            "Sport",
            "Musica",
            "Cucina",
            "Lettura",
            "Arte",
            "Cinema",
            "Teatro",
            "Viaggi",
            "Giochi",
            "Tecnologia",
            "Negozi","Social","Famiglia","Amici","Altro"
    };

    private static final DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));

    Friend(String Name, String Surname) {
        this.Name = Name;
        this.Surname = Surname;
        this.sex = s[rand.nextInt(s.length)];
        this.hobby = hobbies[rand.nextInt(hobbies.length)];
        this.n= new BigDecimal(rand.nextFloat() * 200);
        this.balance=Float.parseFloat(df.format(this.n));

    }

    Friend(String Name, String Surname,String sesso, String hobby,String balance){
        this.Name = Name;
        this.Surname = Surname;
        this.sex = sesso;
        this.hobby = hobby;
        this.balance=Float.parseFloat(balance);
        System.out.println("amico creato sus");
    }

    Friend() {
        Name = "Name";
        Surname = "Surname";
        sex="M";
        hobby = hobbies[rand.nextInt(hobbies.length)];
        balance = rand.nextFloat() * 200;
    }


    public  float getBalance() {
        return balance;
    }
    public void setBalance(float Balance) {
        this.balance = Balance;
    }

    public String getHobby() {
        return hobby;
    }
    public void setHobby(String h){
        this.hobby = h;
    }

    public String getSurname() {
        return Surname;
    }
    public String getName() {
        return Name;
    }
    public String getSex(){
        return sex;
    }
    public void setSex(String s){
        this.sex = s;
    }

    public void ToStampa(){
        System.out.println("Name: "+Name);
        System.out.println("Surname: "+ Surname);
        System.out.println("Hobby: "+hobby);
        System.out.println("Balance: "+balance);
    }


    // genero un numero da sotrarre dalla persona e settare il nuovo Balancel il return serve solo per far avere il valore alla classe kogo
    static float Perdita(Friend a) throws IOException {
        float f,r,s= rand.nextFloat() *50;
        r=a.getBalance()-s;
        if(r >=0 ){
            a.setBalance(r);
            return s;
        }
        // qua sarebbe figo se a dipendere da quanto va in negativo succedono diverse reazioni dell amico come    rissa denuncia o robe cosi
        else{
            f=a.getBalance();
            a.setBalance(0);
            return f;
        }


    }

    // aggiunge un amico nel apposito file
    static void AddToFile(Friend am, boolean append) {

        try (FileWriter fil = new FileWriter(file, append);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf)) {

            if (file.length()==0) {

                wr.println("Name,Surname,Sex,Hobby,Balance"); // Intestazione
            }

            wr.printf(Locale.US,"%s,%s,%s,%s,%.2f\n", am.getName(), am.getSurname(), am.getSex(), am.getHobby(), am.getBalance());

        } catch (Exception e) {
            System.out.println("Errore nella scrittura del file: " + e.getMessage());
        }
    }


    //elimina un amico dalla lista e dal file
    static void DeleteFromFile(Friend am) throws IOException{
        File tempFile = new File("temp.csv");


        try( BufferedReader br = new BufferedReader(new FileReader(file));
             FileWriter fil = new FileWriter("temp.csv",false);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf);)  {
            if(!file.exists())
                file.createNewFile();
            String line;
            while ((line = br.readLine()) != null) {
                // Salta la riga di testo
                if(line.contains(am.getSurname()) && line.contains(am.getName())){

                }
                else
                    wr.println(line);
            }
        }

        try( BufferedReader br = new BufferedReader(new FileReader(tempFile));
             FileWriter fil = new FileWriter("Friends.csv",false);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf);){

            if(!file.exists())
                file.createNewFile();
            String line;
            while ((line = br.readLine()) != null) {
                wr.println(line);
            }
        }
        //tempFile.delete();

    }


    // va a modificare il valore saldo di un certo amico e riscrive il file modificato
    // sarebbe meglio tenere conto delle modifiche fatte e tenere un file che tiene il log delle azioni fatte
    static void Update(Friend am) throws IOException {
        File tempFile = new File("temp.csv");

        // Verifica che il file originale esista prima di leggerlo
        if (!file.exists()) {
            System.out.println("Il file originale non esiste!");
            return;
        }

        boolean modificato = false; // Flag per vedere se abbiamo fatto una modifica

        try (BufferedReader br = new BufferedReader(new FileReader(file));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                // Controlla se la riga corrisponde all'amico da modificare
                if (line.contains(am.getSurname()) && line.contains(am.getName())) {
                    // Scrivi la riga modificata nel file temporaneo
                    bw.write(String.format(Locale.US,"%s,%s,%s,%s,%.2f\n",
                            am.getName(), am.getSurname(), am.getSex(), am.getHobby(), am.getBalance()));
                    modificato = true;
                } else {
                    // Scrivi la riga originale
                    bw.write(line + System.lineSeparator());
                }
            }
        }

        if (!modificato) {
            System.out.println("Nessun amico trovato con il nome e cognome specificati.");
            return; // Non serve sovrascrivere il file se nulla è cambiato
        }

        // Sovrascrivi il file originale con il file temporaneo
        try (BufferedReader br = new BufferedReader(new FileReader(tempFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line + System.lineSeparator());
            }
        }

        // Cancella il file temporaneo dopo la copia
        if (!tempFile.delete()) {
            System.out.println("Impossibile eliminare il file temporaneo.");
        } else {
            System.out.println("File aggiornato con successo!");
        }
    }


    static void CleanFile() throws IOException{
        try(
                FileWriter fil = new FileWriter("Friends.csv",false);){
            fil.close();
        }
    }

    static ObservableList<String> getNames() throws IOException {
        File file = new File("Friends.csv");

        ObservableList<String> names = FXCollections.observableArrayList();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        int i = 0;
        while ((line = br.readLine()) != null) {
            i++;
            String[] s = line.split(",");

            if (s.length >= 2) {
                String res = s[0] + " " + s[1];
                if (i != 1) { // Salta l'intestazione
                    names.add(res);
                }
            } else {
                System.out.println("Errore nel file: Riga non valida -> " + line);
            }
        }
        br.close();
        return names;
    }


}
