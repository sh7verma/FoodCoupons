package app.com.foodcoupons.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.UserModel;
import app.com.foodcoupons.utils.Connection_Detector;
import app.com.foodcoupons.utils.Const;
import app.com.foodcoupons.utils.Encode;
import app.com.foodcoupons.utils.LoadingDialog;
import app.com.foodcoupons.utils.MarshMallowPermission;
import app.com.foodcoupons.utils.Utils;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public MarshMallowPermission mPermission;
    protected int mWidth, mHeight;
    protected Context mContext;
    protected String errorInternet;
    protected String platformStatus = "0";
    protected String deviceToken = "0";
    protected String errorAPI;
    protected String errorAccessToken;
    protected String terminateAccount;
    Utils utils;
    Gson mGson = new Gson();
    Encode encode;
    Typeface typefaceLight, typefaceRegular, typefaceBold, typefaceSemiBold;
    String TAG;
    private Snackbar mSnackbar;

    public static void hideKeyboard(Activity mContext) {
        // Check if no view has focus:
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboardDialog(Activity mContext) {
        // Check if no view has focus:
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(this);
        setContentView(getContentView());
        utils = new Utils(BaseActivity.this);
        mContext = getContext();
        typefaceLight = Typeface.createFromAsset(getAssets(), "fonts/Brandon_light.otf");
        typefaceRegular = Typeface.createFromAsset(getAssets(), "fonts/Brandon_reg.otf");
        typefaceBold = Typeface.createFromAsset(getAssets(), "fonts/Brandon_bld.otf");
        typefaceSemiBold = Typeface.createFromAsset(getAssets(), "fonts/Brandon_med.otf");
        TAG = mContext.getClass().getName();
//        mRoomDb = Room.databaseBuilder(mContext.getApplicationContext(),
//                RoomDb.class, "nass-db").allowMainThreadQueries().fallbackToDestructiveMigration().build();

        ButterKnife.bind(this);
//        db = new Db(this);
        encode = new Encode();
        getDefaults();
        initUI();
        initListener();
        onCreateStuff();
        mPermission = new MarshMallowPermission(this);
        errorInternet = getResources().getString(R.string.internet);
        errorAPI = getResources().getString(R.string.error);
        errorAccessToken = getResources().getString(R.string.invalid_access_token);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onPosted();
        onStarted();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    protected abstract int getContentView();

    protected void showProgress() {
        LoadingDialog.getLoader().showLoader(BaseActivity.this);
    }

    protected void hideProgress() {
        LoadingDialog.getLoader().dismissLoader();
    }

    //onCreate
    protected abstract void onCreateStuff();

    //onStart
    protected void onStarted() {

    }

    //onPostCreate
    protected void onPosted() {

    }

    protected abstract void initUI();

    protected abstract void initListener();

    protected void getDefaults() {
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        mWidth = display.widthPixels;
        mHeight = display.heightPixels;
        Log.e("Height = ", mHeight + " width " + mWidth);
        utils.setInt("width", mWidth);
        utils.setInt("height", mHeight);
    }

//    protected void moveToSplash() {
//        Toast.makeText(mContext, "Someone else login on another device with your credentials", Toast.LENGTH_LONG).show();
//        db.deleteAllTables();
//        utils.clear_shf();
//        Intent inSplash = new Intent(mContext, AfterWalkthroughActivity.class);
//        inSplash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        inSplash.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(inSplash);
//        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
//    }

    protected void showSnackBar(View view, String message) {
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    protected abstract Context getContext();

    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    public void launchActivity(Class<?> clss ,Manifest manifest) {
//        if (ContextCompat.checkSelfPermission(this, manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
//        } else {
//            Intent intent = new Intent(this, clss);
//            startActivityForResult(intent, 2);
//        }
//    }

    protected void showAlertSnackBar(View view, String message) {
        mSnackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        mSnackbar.getView().setBackgroundColor(Color.RED);
        mSnackbar.show();
    }

    public boolean connectedToInternet() {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean connectedToInternet(View view) {
        if ((new Connection_Detector(mContext)).isConnectingToInternet()) {
            return true;
        } else {
            showInternetAlert(view);
            return false;
        }
    }

    protected void showInternetAlert(View view) {
        mSnackbar = Snackbar.make(view, errorInternet, Snackbar.LENGTH_SHORT);
        mSnackbar.show();
    }

    protected void showCustomSnackBar(View containerLayout, String header, String message) {
        LayoutInflater mInflater = LayoutInflater.from(containerLayout.getContext());

        // Create the Snackbar
        mSnackbar = Snackbar.make(containerLayout, message, Snackbar.LENGTH_LONG);
        mSnackbar.getView().setBackgroundColor(Color.TRANSPARENT);
        mSnackbar.getView().setBackground(mContext.getResources().getDrawable(R.drawable.primary_top_round));

        // Get the Snackbar's layout view
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) mSnackbar.getView();
        // Hide the text
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        // Inflate our custom view
        View snackView = mInflater.inflate(R.layout.my_snackbar, null);
        // Configure the view
        TextView textViewTop = (TextView) snackView.findViewById(R.id.text);
        textViewTop.setText(message);
        textViewTop.setTextColor(Color.WHITE);

        TextView txtHeader = (TextView) snackView.findViewById(R.id.txt_header);
        txtHeader.setText(header);
        txtHeader.setTextColor(Color.WHITE);

        // Add the view to the Snackbar's layout
        layout.addView(snackView, 0);
        // Show the Snackbar
        mSnackbar.show();
    }

    protected RequestBody createPartFromString(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    protected MultipartBody.Part createFilePart(File mFile, String name) {
        RequestBody reqFile;
        if (mFile != null) {
            reqFile = RequestBody.create(MediaType.parse("image/*"), mFile);
            return MultipartBody.Part.createFormData(name, mFile.getName(), reqFile);
        } else {
            reqFile = RequestBody.create(MediaType.parse("image/*"), "");
            return MultipartBody.Part.createFormData("", "", reqFile);
        }
    }


    void setUserData(UserModel body) {
        utils.setInt(Const.ID, body.getData().getId());
        utils.setString(Const.NAME, body.getData().getName());
        utils.setString(Const.EMAIL, body.getData().getEmail());
        utils.setString(Const.COUNTRY_CODE, body.getData().getCountry_code());
        utils.setString(Const.PHONE_NO, body.getData().getPhone_number());
        utils.setString(Const.PROFILE_PIC, body.getData().getProfile_pic());
        utils.setString(Const.ACCESS_TOKEN, body.getData().getAccess_token());
        utils.setString(Const.SOCIAL_MEDIA_ID, body.getData().getSocial_media_id());
        utils.setInt(Const.PROFILE_STATUS, body.getData().getProfile_status());
        utils.setInt(Const.LOGIN_VIA, body.getData().getLogin_via());
    }


}
