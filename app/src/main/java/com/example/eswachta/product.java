package com.example.eswachta;

import java.io.Serializable;
import java.time.LocalDate;

public class product implements Serializable {
    public String name;
    public String description;
    public String price;
    public String workingStat;
    public String damagesStat;
    public String damageDetail;
    public LocalDate dateOfPurchase;
    product(String name,String description,String price,String workingStat,String damagesStat,String damageDetail,LocalDate dateOfPurchase){
        this.name=name;
        this.description=description;
        this.price=price;
        this.workingStat=workingStat;
        this.damagesStat=damagesStat;
        this.damageDetail=damageDetail;
        this.dateOfPurchase=dateOfPurchase;
    }
    public product(){

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getWorkingStat() {
        return workingStat;
    }

    public String getDamagesStat() {
        return damagesStat;
    }

    public String getDamageDetail() {
        return damageDetail;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }
}
