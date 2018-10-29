package app.com.foodcoupons.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 10/10/18.
 */

public class FilterModel extends BaseModel{
    /**
     * statusCode : 200
     * message : success
     * brands : [{"id":1,"name":"sdfsd"},{"id":2,"name":"kfc"},{"id":3,"name":"dominos"}]
     */

    List<BrandModel> brands = new ArrayList<>();
    List<BrandModel> cuisine = new ArrayList<>();
    List<PriceModel> price = new ArrayList<>();

    String sortPrice = "0";

    public List<PriceModel> getPrice() {
        return price;
    }

    public void setPrice(List<PriceModel> price) {
        this.price = price;
    }

    public String getSortPrice() {
        return sortPrice;
    }

    public void setSortPrice(String sortPrice) {
        this.sortPrice = sortPrice;
    }

    public List<BrandModel> getBrands() {
        return brands;
    }

    public void setBrands(List<BrandModel> brands) {
        this.brands = brands;
    }

    public List<BrandModel> getCuisine() {
        return cuisine;
    }

    public void setCuisine(List<BrandModel> cuisine) {
        this.cuisine = cuisine;
    }

    public static class BrandModel extends BaseModel {

        private String name;
        private int id;
        private boolean Selected;


        public BrandModel(String mName, int mId, boolean Selected) {
            this.name = mName;
            this.id = mId;
            this.Selected = Selected;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isSelected() {
            return Selected;
        }

        public void setSelected(boolean selected) {
            this.Selected = selected;
        }
    }

    public static class PriceModel {

        private String min;
        private String max;
        private int id;
        private boolean selected;

        public PriceModel(String mStartValue, String mEndValue, int mId, boolean isSelected) {
            this.min = mStartValue;
            this.max = mEndValue;
            this.id = mId;
            this.selected = isSelected;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }

}
