package app.com.foodcoupons.models;

/**
 * Created by dev on 28/9/18.
 */

public  class BroucherModel {
    private String mPlaceName;
    private String mPlaceAddress;
    private String mDeals;
    private Integer mRating;
    private Integer mImage;

    public BroucherModel(String mPlaceName, String mPlaceAddress, String mDeals, Integer mRating, Integer mImage) {
        this.mPlaceName = mPlaceName;
        this.mPlaceAddress = mPlaceAddress;
        this.mDeals = mDeals;
        this.mRating = mRating;
        this.mImage = mImage;
    }

    public String getmPlaceName() {
        return mPlaceName;
    }

    public void setmPlaceName(String mPlaceName) {
        this.mPlaceName = mPlaceName;
    }

    public String getmPlaceAddress() {
        return mPlaceAddress;
    }

    public void setmPlaceAddress(String mPlaceAddress) {
        this.mPlaceAddress = mPlaceAddress;
    }

    public String getmDeals() {
        return mDeals;
    }

    public void setmDeals(String mDeals) {
        this.mDeals = mDeals;
    }

    public Integer getmRating() {
        return mRating;
    }

    public void setmRating(Integer mRating) {
        this.mRating = mRating;
    }

    public Integer getmImage() {
        return mImage;
    }

    public void setmImage(Integer mImage) {
        this.mImage = mImage;
    }

}
