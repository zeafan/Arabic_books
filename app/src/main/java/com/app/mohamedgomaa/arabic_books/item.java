package com.app.mohamedgomaa.arabic_books;

import java.io.Serializable;

/**
 * Created by Mohamed Gooma on 9/18/2017.
 */

public class item implements Serializable{
     int  id;
    String title_ar,title_en,details_en,details_ar,pth_photo,pth_review,pth_cd,pth_book;
    double price;

    public item(int id, String title_ar, String title_en, String details_en, String details_ar, String pth_photo, String pth_review, String pth_cd, String pth_book, double price) {
        this.id = id;
        this.title_ar = title_ar;
        this.title_en = title_en;
        this.details_en = details_en;
        this.details_ar = details_ar;
        this.pth_photo = pth_photo;
        this.pth_review = pth_review;
        this.pth_cd = pth_cd;
        this.pth_book = pth_book;
        this.price = price;
    }

}
