package app.com.foodcoupons.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dev on 29/9/18.
 */

public class DealModel extends BaseModel {

    DealsBean deal_detail;
    /**
     * code : 200
     * message : success
     * deals : [
     * <p>
     * {"id":1,"deal_name":"sf","description":"sdf","valid_upto":"2018-10-10T08:06:00.000Z","distance":5880.264621678765,
     * "deal_images":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/1_landing7869661.jpg"}
     * <p>
     * ,{"id":2,"deal_name":"sf","description":"sdf","valid_upto":"2018-10-10T08:06:00.000Z",
     * "distance":5880.264621678765,"deal_images":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/1_landing7869661.jpg"}
     * ]
     */

    private ArrayList<DealsBean> deals;

    public ArrayList<DealsBean> getDeals() {
        return deals;
    }

    public void setDeals(ArrayList<DealsBean> deals) {
        this.deals = deals;
    }

    public DealsBean getDeal_detail() {
        return deal_detail;
    }

    public void setDeal_detail(DealsBean deal_detail) {
        this.deal_detail = deal_detail;
    }

    public static class DealsBean implements Parcelable {

        public static final Creator<DealsBean> CREATOR = new Creator<DealsBean>() {
            @Override
            public DealsBean createFromParcel(Parcel source) {
                return new DealsBean(source);
            }

            @Override
            public DealsBean[] newArray(int size) {
                return new DealsBean[size];
            }
        };
        /**
         * id : 1
         * deal_name : sf
         * description : sdf
         * valid_upto : 2018-10-10T08:06:00.000Z
         * distance : 5880.264621678765
         * deal_images : https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/1_landing7869661.jpg
         * deal_images : [{"id":6,"image":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/noodels.png"}]
         * offer_detail : dsf
         * deal_price : 6
         * orignal_price : 23
         * discounted_price : 21
         * location : sadsa
         * latitude : 28.48419000
         * longitude : 76.99551000
         * offer_description : dfgd
         * terms_condition : dfgfd
         * redemption_instruction : fdgdf
         * rating : 5
         * phone_number :
         */

        private int id;
        private String deal_name;
        private String description;
        private String valid_upto;
        private double distance;
        private String offer_detail;
        private int deal_price;
        private int orignal_price;
        private int discounted_price;
        private String location;
        private String latitude;
        private String longitude;
        private String offer_description;
        private String terms_condition;
        private String redemption_instruction;
        private int rating;
        private String phone_number;
        private int outlet_id;
        private List<ImagesBean> deal_images;
        private String image;

        public DealsBean() {
        }

        protected DealsBean(Parcel in) {
            this.id = in.readInt();
            this.deal_name = in.readString();
            this.description = in.readString();
            this.valid_upto = in.readString();
            this.distance = in.readDouble();
            this.offer_detail = in.readString();
            this.deal_price = in.readInt();
            this.orignal_price = in.readInt();
            this.discounted_price = in.readInt();
            this.location = in.readString();
            this.latitude = in.readString();
            this.longitude = in.readString();
            this.offer_description = in.readString();
            this.terms_condition = in.readString();
            this.redemption_instruction = in.readString();
            this.rating = in.readInt();
            this.phone_number = in.readString();
            this.outlet_id = in.readInt();
            this.deal_images = in.createTypedArrayList(ImagesBean.CREATOR);
            this.image = in.readString();
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getOutlet_id() {
            return outlet_id;
        }

        public void setOutlet_id(int outlet_id) {
            this.outlet_id = outlet_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDeal_name() {
            return deal_name;
        }

        public void setDeal_name(String deal_name) {
            this.deal_name = deal_name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getValid_upto() {
            return valid_upto;
        }

        public void setValid_upto(String valid_upto) {
            this.valid_upto = valid_upto;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public String getOffer_detail() {
            return offer_detail;
        }

        public void setOffer_detail(String offer_detail) {
            this.offer_detail = offer_detail;
        }

        public int getDeal_price() {
            return deal_price;
        }

        public void setDeal_price(int deal_price) {
            this.deal_price = deal_price;
        }

        public int getOrignal_price() {
            return orignal_price;
        }

        public void setOrignal_price(int orignal_price) {
            this.orignal_price = orignal_price;
        }

        public int getDiscounted_price() {
            return discounted_price;
        }

        public void setDiscounted_price(int discounted_price) {
            this.discounted_price = discounted_price;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOffer_description() {
            return offer_description;
        }

        public void setOffer_description(String offer_description) {
            this.offer_description = offer_description;
        }

        public String getTerms_condition() {
            return terms_condition;
        }

        public void setTerms_condition(String terms_condition) {
            this.terms_condition = terms_condition;
        }

        public String getRedemption_instruction() {
            return redemption_instruction;
        }

        public void setRedemption_instruction(String redemption_instruction) {
            this.redemption_instruction = redemption_instruction;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public List<ImagesBean> getDeal_images() {
            return deal_images;
        }

        public void setDeal_images(List<ImagesBean> deal_images) {
            this.deal_images = deal_images;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.deal_name);
            dest.writeString(this.description);
            dest.writeString(this.valid_upto);
            dest.writeDouble(this.distance);
            dest.writeString(this.offer_detail);
            dest.writeInt(this.deal_price);
            dest.writeInt(this.orignal_price);
            dest.writeInt(this.discounted_price);
            dest.writeString(this.location);
            dest.writeString(this.latitude);
            dest.writeString(this.longitude);
            dest.writeString(this.offer_description);
            dest.writeString(this.terms_condition);
            dest.writeString(this.redemption_instruction);
            dest.writeInt(this.rating);
            dest.writeString(this.phone_number);
            dest.writeInt(this.outlet_id);
            dest.writeTypedList(this.deal_images);
            dest.writeString(this.image);
        }

        public static class ImagesBean implements Parcelable {
            public static final Creator<ImagesBean> CREATOR = new Creator<ImagesBean>() {
                @Override
                public ImagesBean createFromParcel(Parcel source) {
                    return new ImagesBean(source);
                }

                @Override
                public ImagesBean[] newArray(int size) {
                    return new ImagesBean[size];
                }
            };
            /**
             * id : 6
             * image : https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/deals/noodels.png
             */

            @SerializedName("id")
            private int idX;
            private String image;

            public ImagesBean() {
            }

            protected ImagesBean(Parcel in) {
                this.idX = in.readInt();
                this.image = in.readString();
            }

            public int getIdX() {
                return idX;
            }

            public void setIdX(int idX) {
                this.idX = idX;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.idX);
                dest.writeString(this.image);
            }
        }
    }
}
