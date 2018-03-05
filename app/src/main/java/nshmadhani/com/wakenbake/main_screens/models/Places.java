package nshmadhani.com.wakenbake.main_screens.models;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import nshmadhani.com.wakenbake.main_screens.activities.NavigationActivity;

/**
 * Created by Nachiket on 03-Feb-18.
 */

public class Places {

    @SerializedName("name")
    public String name;

    @SerializedName("number")
    public String phoneNumber;

    @SerializedName("ratings")
    public double ratings;

    @SerializedName("open")
    public int openTime;

    @SerializedName("close")
    public int closeTime;

    @SerializedName("fooditems")
    public String foodItems;

    //Additional required for Google Places
    public String photoReference;
    public String placeId;
    public String placeAddress;
    public String imageUrl;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getRatings() {
        return ratings;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public String getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(String foodItems) {
        this.foodItems = foodItems;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }


}
