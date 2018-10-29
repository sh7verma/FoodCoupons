package app.com.foodcoupons.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 9/10/18.
 */

public class FilterSingletonModel extends BaseModel {

    private static FilterSingletonModel instance;

    /**
     * statusCode : 200
     * message : success
     * brands : [{"id":1,"name":"sdfsd"},{"id":2,"name":"kfc"},{"id":3,"name":"dominos"}]
     */

    List<BrandModel> brands = new ArrayList<>();
    List<BrandModel> cuisine = new ArrayList<>();
    List<PriceModel> price = new ArrayList<>();

    String sortPrice = "0";

    public static FilterSingletonModel getInstance() {
        if (instance == null) {
            instance = new FilterSingletonModel();
        }
        return instance;
    }

    public static FilterSingletonModel resetInstance() {
        FilterSingletonModel.instance = null;
        return FilterSingletonModel.getInstance();
    }

    public void resetData() {

        for (int i = 0; i < instance.getCuisine().size(); i++) {
            instance.getCuisine().get(i).setSelected(false);
        }

        for (int i = 0; i < instance.getBrands().size(); i++) {
            instance.getBrands().get(i).setSelected(false);
        }

        for (int i = 0; i < instance.getPrice().size(); i++) {
            instance.getPrice().get(i).setSelected(false);
        }
    }


    public List<PriceModel> getPrice() {
        return price;
    }

    public void setPrice(List<PriceModel> price) {
        this.price = price;
    }

    public String getMaxPricePriceSelected() {
        String maxPrice = "";
        for (int i = 0; i < instance.getPrice().size(); i++) {
            if (instance.getPrice().get(i).isSelected()) {
                maxPrice = instance.getPrice().get(i).getMax();
            }
        }
        return maxPrice;
    }

    public String getMinPricePriceSelected() {
        String min = "";
        for (int i = 0; i < instance.getPrice().size(); i++) {
            if (instance.getPrice().get(i).isSelected()) {
                min = instance.getPrice().get(i).getMin();
            }
        }
        return min;
    }

    public String getCuisineSelected() {
        StringBuilder cuisineIds = new StringBuilder();
        for (int i = 0; i < instance.getCuisine().size(); i++) {
            if (instance.getCuisine().get(i).isSelected()) {
                cuisineIds.append(instance.getCuisine().get(i).getId() + ",");
            }
        }
        if (cuisineIds.toString().endsWith(",")) {
            return cuisineIds.toString().substring(0, cuisineIds.toString().length() - 1).trim();
        }
        return cuisineIds.toString().trim();
    }

    public String getBrandSelected() {
        StringBuilder brandIds = new StringBuilder();
        for (int i = 0; i < instance.getBrands().size(); i++) {
            if (instance.getBrands().get(i).isSelected()) {
                brandIds.append(instance.getBrands().get(i).getId() + ",");
            }
        }
        if (brandIds.toString().endsWith(",")) {
            return brandIds.toString().substring(0, brandIds.toString().length() - 1).trim();
        }
        return brandIds.toString().trim();

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
