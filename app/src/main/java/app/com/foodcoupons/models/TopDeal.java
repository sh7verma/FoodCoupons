package app.com.foodcoupons.models;

import java.util.List;

/**
 * Created by dev on 8/10/18.
 */

public class TopDeal extends BaseModel {


    /**
     * statusCode : 200
     * message : success
     * deal : [{"id":2,"description":"sdf","distance":5880.264025482384,"images":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/1_landing7869661.jpg"}]
     */

    private List<DealBean> deal;

    public List<DealBean> getDeal() {
        return deal;
    }

    public void setDeal(List<DealBean> deal) {
        this.deal = deal;
    }

    public static class DealBean {
        /**
         * id : 2
         * description : sdf
         * distance : 5880.264025482384
         * images : https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/1_landing7869661.jpg
         */

        private int id;
        private String description;
        private double distance;
        private String images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }
    }
}
