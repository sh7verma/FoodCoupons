package app.com.foodcoupons.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by dev on 28/9/18.
 */

public class CategoryModel extends BaseModel {

    /**
     * status : 200
     * message : success
     * data : [{"id":1,"category":"chinese","image":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/categories/chinese.jpg"},{"id":2,"category":"chinese1","image":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/categories/chinese1.png"}]
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
        /**
         * id : 1
         * category : chinese
         * image : https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/categories/chinese.jpg
         */

        private int id;
        private String category;
        private String image;

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.id = in.readInt();
            this.category = in.readString();
            this.image = in.readString();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
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
            dest.writeInt(this.id);
            dest.writeString(this.category);
            dest.writeString(this.image);
        }
    }
}
