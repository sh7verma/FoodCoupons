package app.com.foodcoupons.utils;

/**
 * Created by dev on 27/9/18.
 */

public interface Const {

    int BAD_REQUEST = 400;
    int RESPONSE_SUCCESS = 200;

    String PHONE_NO = "PHONE_NO";
    String COUNTRY_CODE = "COUNTRY_CODE";
    String ACCESS_TOKEN = "access_token";
    String ID = "id";
    String NAME = "NAME";
    String EMAIL = "EMAIL";
    String PROFILE_PIC = "PROFILE_PIC";
    String SOCIAL_MEDIA_ID = "SOCIAL_MEDIA_ID";
    String PROFILE_STATUS = "PROFILE_STATUS";

    int PROFILE_IS_CREATED = 1;
    int OTP_IS_VERIFIED = 1;

    int REQ_GOOGLE = 1001;
    int REQ_PIC = 100;

    int FRAG_HOME = 0;
    int FRAG_NEARBY = 1;
    int FRAG_BROCHURES = 2;
    int FRAG_PROFILE = 3;

    int FRAG_NULL = 0;
    int REQ_CODE_COUNTRY = 1405;
    String LOGIN_VIA = "login_via";
    int SOCIAL_LOGIN = 0;
    String INTRO = "intro";
    String LATITUDE = "LATITUDE";
    String LONGITUDE = "LONGITUDE";
    String ADDRESS = "address";
    String EXTRA = "EXTRA";
    String TYPE = "type";
    String TYPE_NEAR_BY_DEALS = "1";
    String TYPE_ELSE = "2";
    String TYPE_DEALS = "0";
    String CURRENCY="$";
}
