package app.com.foodcoupons.models;

/**
 * Created by dev on 27/9/18.
 */

public class UserModel extends BaseModel {
    /**
     * data : {"id":29,"NAME":" ","EMAIL":" ","country_code":"91","phone_number":"9866146568","PROFILE_PIC":"https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/pictures/ ","access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ijk4NjYxNDY1NjgiLCJ0eXBlIjoidXNlciIsImlhdCI6MTUzODA0ODMwN30.03fXZ3LiZ4PPuicHrM_XzZ7qgGC0_4FFEwP_7grNV3g","SOCIAL_MEDIA_ID":" ","PROFILE_STATUS":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 29
         * NAME :
         * EMAIL :
         * country_code : 91
         * phone_number : 9866146568
         * PROFILE_PIC : https://s3.ap-south-1.amazonaws.com/kittydev/fastFood/pictures/
         * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6Ijk4NjYxNDY1NjgiLCJ0eXBlIjoidXNlciIsImlhdCI6MTUzODA0ODMwN30.03fXZ3LiZ4PPuicHrM_XzZ7qgGC0_4FFEwP_7grNV3g
         * SOCIAL_MEDIA_ID :
         * PROFILE_STATUS : 0
         */

        private int id;
        private String name;
        private String email;
        private String country_code;
        private String phone_number;
        private String profile_pic;
        private String access_token;
        private String social_media_id;
        private int profile_status;
        private int login_via;

        public int getLogin_via() {
            return login_via;
        }

        public void setLogin_via(int login_via) {
            this.login_via = login_via;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name.trim();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email.trim();
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code.trim();
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number.trim();
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic.trim();
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getSocial_media_id() {
            return social_media_id;
        }

        public void setSocial_media_id(String social_media_id) {
            this.social_media_id = social_media_id;
        }

        public int getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(int profile_status) {
            this.profile_status = profile_status;
        }
    }

}
