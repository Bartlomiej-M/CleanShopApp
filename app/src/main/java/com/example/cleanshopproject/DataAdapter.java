package com.example.cleanshopproject;

public class DataAdapter
{
    public String ImageID;
    public String ImageURL;
    public String ImageTitle;
    public String ImagePrice;
    public String ImageDesc;
    public String ImageCategory;
    public String ImageNumber;


    public String getImageID() {
        return ImageID;
    }

    public String getImageUrl() {
        return ImageURL;
    }

    public void setImageUrl(String imageServerUrl) {
        this.ImageURL = imageServerUrl;
    }

    public String getImageTitle() {
        return ImageTitle;
    }

    public String getImagePrice() {
        return ImagePrice;
    }

    public String getImageDesc() {
        return ImageDesc;
    }

    public String getImageCategory() {
        return ImageCategory;
    }

    public String getImageNumber() {
        return ImageNumber;
    }


    public void setImageID(String ImageID) {
        this.ImageTitle = ImageID;
    }

    public void setImageTitle(String Imagetitlename) {
        this.ImageTitle = Imagetitlename;
    }

    public void setImagePrice(String Imagetitleprice) {
        this.ImagePrice = Imagetitleprice;
    }

    public void setImageDesc(String Imagetitledesc) {
        this.ImageDesc = Imagetitledesc;
    }

    public void setImageCategory(String Imagetitlecategory){this.ImageCategory = Imagetitlecategory;}

    public void setImageNumber(String ImageNumber){this.ImageNumber = ImageNumber;}


}