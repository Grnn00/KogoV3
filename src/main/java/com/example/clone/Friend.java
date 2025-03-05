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
    Random rand = new Random();
    File file = new File("Friends.txt");

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

    Friend(String Name, String Surname,String sesso, String hobby,float balance){
        this.Name = Name;
        this.Surname = Surname;
        this.sex = sesso;
        this.hobby = hobby;
        this.n= new BigDecimal(balance);
        this.balance=Float.parseFloat(df.format(this.n));
        System.out.println("amico creato sus");
    }

    Friend() {
        Name = "Name";
        Surname = "Surname";
        sex="M";
        hobby = hobbies[rand.nextInt(hobbies.length)];
        balance = rand.nextFloat() * 200;
    }


    public float getBalance() {
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
    public float Perdita(Friend a) throws IOException {
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
    public void AddToFile(Friend am,Boolean condition) {
        try(BufferedReader br = new BufferedReader(new FileReader(file));
            FileWriter fil = new FileWriter("Friends.txt",condition);
            BufferedWriter bf = new BufferedWriter(fil);
            PrintWriter wr = new PrintWriter(bf);)  {
            if(!file.exists())
                file.createNewFile();

            if(br.readLine()==null)
                wr.printf("Name\t\tSurname\t\tSex\t\tHobby\t\tBalance\n");

            wr.printf("%s\t\t%s\t\t%s\t\t%s\t\t%.2f\n", am.getName(), am.getSurname(),am.getSex (), am.getHobby(), am.getBalance());

        } catch (Exception e) {
            System.out.println("dio porco"  + e);
        }
    }

    //elimina un amico dalla lista e dal file
    public void DeleteFromFile(Friend am) throws IOException{
        File tempFile = new File("temp.txt");

        try( BufferedReader br = new BufferedReader(new FileReader(file));
             FileWriter fil = new FileWriter("temp.txt",false);
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
             FileWriter fil = new FileWriter("Friends.txt",false);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf);){

            if(!file.exists())
                file.createNewFile();
            String line;
            while ((line = br.readLine()) != null) {
                wr.println(line);
            }
        }
        tempFile.delete();

    }


    // va a modificare il valore saldo di un certo amico e riscrive il file modificato
    // sarebbe meglio tenere conto delle modifiche fatte e tenere un file che tiene il log delle azioni fatte
    public void Update(Friend am) throws IOException {

        File tempFile = new File("temp.txt");

        try( BufferedReader br = new BufferedReader(new FileReader(file));
             FileWriter fil = new FileWriter("temp.txt",false);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf);)  {
            if(!file.exists())
                file.createNewFile();
            String line;
            while ((line = br.readLine()) != null) {
                // Modifica la riga di testo
                if(line.contains(am.getSurname()) && line.contains(am.getName())){
                    // Scrivi la riga di testo modificata sul file
                    wr.printf("%s\t\t%s\t\t%s\t\t%s\t\t%.2f\n", am.getName(),am.getSurname(),am.getSex(),am.getHobby(),am.getBalance());

                }
                else
                    wr.println(line);
            }
        }

        try( BufferedReader br = new BufferedReader(new FileReader(tempFile));
             FileWriter fil = new FileWriter("Friends.txt",false);
             BufferedWriter bf = new BufferedWriter(fil);
             PrintWriter wr = new PrintWriter(bf);){

            if(!file.exists())
                file.createNewFile();
            String line;
            while ((line = br.readLine()) != null) {
                wr.println(line);
            }
        }
        tempFile.delete();
    }

    public void CleanFile() throws IOException{
        try(
                FileWriter fil = new FileWriter("Friends.txt",false);){
            fil.close();
        }
    }

    public ObservableList<String> getNames() throws IOException{
        ObservableList<String> names = FXCollections.observableArrayList();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String line,n,c,res;
        int i=0;
        while ((line = br.readLine()) != null) {
            i++;
            String s[]= line.split("\t\t");

            n = s[0];
            c = s[1];
            res=n+" "+c;
            //System.out.println("S: "+res );
            if(i!=1){
                names.add(res);
            }
        }
        br.close();
        return names;
    }

}
