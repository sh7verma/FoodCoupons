package app.com.foodcoupons.utils;

import android.text.TextUtils;

/**
 * Created by dev on 25/4/18.
 */

public class Encode {

    private CryptLib cryptLib;
    private String SHA_KEY = "KEYFORISN";
    //    private String SHA_IV = "121245457878";
    private String SHA_IV = "1212454578789090";


    public Encode() {
        try {
            cryptLib = new CryptLib();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String encrypt(String s) {
        String encryptedString = "";
        if (!TextUtils.isEmpty(s)) {
            try {
                encryptedString = cryptLib.encryptSimple(s, SHA_KEY, SHA_IV);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return encryptedString.trim();
        } else {
            return encryptedString;
        }
    }


    public String decrypt(String s) {
        String decryptedString = "";
        if (!TextUtils.isEmpty(s)) {
            try {
                decryptedString = cryptLib.decryptSimple(s, SHA_KEY, SHA_IV);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return decryptedString.trim();
        } else {
            return decryptedString;
        }
    }

}