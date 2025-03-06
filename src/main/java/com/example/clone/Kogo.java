package com.example.clone;

public class Kogo {
    private Float money,stolen_money;
    private String form;

    Kogo(){
        money=Float.valueOf("0");
        form = "Human";
        stolen_money=Float.valueOf("0");
    }


    public Float getMoney() {
        return money;
    }

    public Float getStolen_money() {
        return stolen_money;
    }

    public String getForm() {
        return form;
    }


    public void setMoney(Float money) {
        this.money = money;
    }

    public void setStolen_money(Float stolen_money) {
        this.stolen_money += stolen_money;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public Kogo(Float money, Float stolen_money, String form) {
        this.money = money;
        this.stolen_money = stolen_money;
        this.form = form;
    }

    public void Rubare(Float float1) {
        this.setMoney(this.getMoney()+float1);
        this.setStolen_money(float1);
        Transform(this.getStolen_money());
    }

    public void Transform(Float rub){
        if(rub<=200){
            setForm("Human");
        }
        else if(rub>200 && rub<=500){
            setForm("Monkey");
        }
        else if(rub>500 && rub<=1000){
            setForm("Sayan");
        }
        else if(rub>1000 && rub<=2000){
            setForm("SuperSayan");
        }
        else if(rub>2000 && rub<=5000){
            setForm("Kogorilla");
        }
        else{
            setForm("UltraKogorilla");
        }
    }

    public void Spendere(Float s){
        this.setMoney(this.getMoney()-s);
    }

}
