package com.example.clone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class Friend {
    private String Name,Surname,hobby;
    private float balance;
    private BigDecimal n;   //variabile per formattare il valore in "balance"
    private String sex;      String[] s={"M","F"};
    static Random rand = new Random();

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
        System.out.println("Name: "+this.Name);
        System.out.println("Surname: "+ this.Surname);
        System.out.println("Hobby: "+this.hobby);
        System.out.println("Balance: "+this.balance);
    }


    // genero un numero da sotrarre dalla persona e settare il nuovo Balancel il return serve solo per far avere il valore alla classe kogo
    static float Perdita(Friend a) throws IOException {
        float s= rand.nextFloat() *50;
        float r=(a.getBalance())-s;
        if(r >=0 ){
            a.setBalance(r);
            return s;
        }
        // qua sarebbe figo se a dipendere da quanto va in negativo succedono diverse reazioni dell amico come    rissa denuncia o robe cosi
        else{
          // float f=a.getBalance();
            r=a.getBalance();
            a.setBalance(0);
            return r;
        }


    }

    // aggiunge un amico nel apposito file
    static void AddToFile(Friend am, boolean append) {
        File file = new File("Friends.csv");

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
        File file = new File("Friends.csv");


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
    static void Update(ArrayList<Friend> af) throws IOException {
        File file = new File("Friends.csv");

        try (FileWriter fil = new FileWriter(file, false);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf)) {

            if (file.length()==0) {

                wr.println("Name,Surname,Sex,Hobby,Balance"); // Intestazione
            }
            for(Friend am : af) {
                wr.printf(Locale.US, "%s,%s,%s,%s,%.2f\n", am.getName(), am.getSurname(), am.getSex(), am.getHobby(), am.getBalance());
            }

        } catch (Exception e) {
            System.out.println("Errore nella scrittura del file: " + e.getMessage());
        }

    }


    static void CleanFile() throws IOException{
        try(
                FileWriter fil = new FileWriter("Friends.csv",false);){
            fil.close();
        }
    }


}
