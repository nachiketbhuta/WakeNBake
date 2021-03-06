package nshmadhani.com.wakenbake.Models;

import com.google.gson.annotations.SerializedName;


public class TiffinPlaces  {

    @SerializedName("name")
    public String mTiffinName;

    @SerializedName("number")
    public String mTiffinNumber;

    @SerializedName("fooditems")
    public String mTiffinFoodItems;

    @SerializedName("ratings")
    public float mTiffinRatings;

    @SerializedName("id")
    public String mTiffinId;

    public String getmTiffinId() {
        return mTiffinId;
    }

    public void setmTiffinId(String mTiffinId) {
        this.mTiffinId = mTiffinId;
    }

    public String getmTiffinName() {
        return mTiffinName;
    }

    public void setmTiffinName(String mTiffinName) {
        this.mTiffinName = mTiffinName;
    }

    public String getmTiffinNumber() {
        return mTiffinNumber;
    }

    public void setmTiffinNumber(String mTiffinNumber) {
        this.mTiffinNumber = mTiffinNumber;
    }

    public String getmTiffinFoodItems() {
        return mTiffinFoodItems;
    }

    public void setmTiffinFoodItems(String mTiffinFoodItems) {
        this.mTiffinFoodItems = mTiffinFoodItems;
    }

    public float getmTiffinRatings() {
        return mTiffinRatings;
    }

    public void setmTiffinRatings(float mTiffinRatings) {
        this.mTiffinRatings = mTiffinRatings;
    }
}
