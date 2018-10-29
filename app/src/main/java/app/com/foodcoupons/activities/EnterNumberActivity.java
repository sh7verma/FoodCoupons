package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URL;
import java.util.Arrays;

import app.com.foodcoupons.R;
import app.com.foodcoupons.customviews.MaterialEditText;
import app.com.foodcoupons.models.UserModel;
import app.com.foodcoupons.network.RetrofitClient;
import app.com.foodcoupons.utils.Const;
import app.com.foodcoupons.utils.CountryCodeActivity;
import app.com.foodcoupons.utils.LoadingDialog;
import app.com.foodcoupons.utils.Validations;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 26/9/18.
 */
public class EnterNumberActivity extends BaseActivity {

    private static final int RC_SIGN_IN = 9001;


    @BindView(R.id.ll_next)
    LinearLayout llNext;
    @BindView(R.id.ed_number)
    MaterialEditText edNumber;
    @BindView(R.id.txt_country_code)
    TextView txtCountryCode;
    @BindView(R.id.img_facebook_login)
    ImageView imgFacebookLogin;
    @BindView(R.id.img_gmail_login)
    ImageView imgGmailLogin;

    /// facebook Code
    private CallbackManager callbackManager;
    private String mImageUrl, mName, mId, mEmail;

    //google code
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected int getContentView() {
        return R.layout.activity_enter_number;
    }

    @Override
    protected void onCreateStuff() {
    }

    @Override
    protected void initUI() {
        edNumber.setTypeface(typefaceSemiBold);
    }

    @Override
    protected void initListener() {
        imgFacebookLogin.setOnClickListener(this);
        imgGmailLogin.setOnClickListener(this);
        txtCountryCode.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @OnClick(R.id.ll_next)
    void next() {
        hideKeyboard(this);
        if (Validations.checkPhoneValidation(this, edNumber)) {
            hitApi();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_facebook_login:
                if (connectedToInternet(llNext)) {
                    loginWithFacebook();
                }
                break;
            case R.id.img_gmail_login:
                if (connectedToInternet(llNext)) {
                    signInGmail();
                }
                break;
            case R.id.txt_country_code:
                Intent inSelectCountry = new Intent(mContext, CountryCodeActivity.class);
                startActivityForResult(inSelectCountry, Const.REQ_CODE_COUNTRY);
                break;

        }
    }

    private void hitApi() {
        if (connectedToInternet(llNext)) {
            LoadingDialog.getLoader().showLoader(mContext);
            Call<UserModel> call = RetrofitClient.getInstance().phone_registration("0", txtCountryCode.getText().toString(),
                    edNumber.getText().toString(), deviceToken, platformStatus);
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    LoadingDialog.getLoader().dismissLoader();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                        setUserData(response.body());

                        Intent intent = new Intent(mContext, OtpVerifyActivity.class);
                        intent.putExtra(Const.COUNTRY_CODE, txtCountryCode.getText().toString());
                        intent.putExtra(Const.PHONE_NO, edNumber.getText().toString());
                        startActivity(intent);

                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(llNext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                    LoadingDialog.getLoader().dismissLoader();
                    t.printStackTrace();
                }
            });
        }
    }


    void loginWithFacebook() {
        LoadingDialog.getLoader().showLoader(mContext);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        fetchDataFacebook(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        LoadingDialog.getLoader().dismissLoader();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        LoadingDialog.getLoader().dismissLoader();
                        exception.printStackTrace();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SIGN_IN:
                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    handleSignInResult(task);
                    break;
                case Const.REQ_CODE_COUNTRY:
                    txtCountryCode.setText(data.getStringExtra("Country_code"));
                    break;
                default:
                    callbackManager.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }
    }

    private void fetchDataFacebook(LoginResult loginResult) {
        if (connectedToInternet(llNext)) {
            LoadingDialog.getLoader().showLoader(mContext);
            AccessToken accessToken = loginResult.getAccessToken();
            AccessToken.setCurrentAccessToken(accessToken);
            String ac = new Gson().toJson(accessToken);
            utils.setString("fb_accessToken", ac);
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {

                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    LoadingDialog.getLoader().dismissLoader();

                    // TODO Auto-generated method stub
                    Log.e("data", "" + object + "");
                    try {
                        String id = object.getString("id");
                        URL image_value = new URL("http://graph.facebook.com/" + object.getString("id") + "/picture?width=640&&height=640");
                        Log.e("url", image_value + "");
                        if (connectedToInternet()) {
                            mImageUrl = image_value + "";
                            mName = object.getString("name");
                            mId = object.getString("id");
                            if (object.has("email")) {
                                mEmail = object.getString("email");
                            } else {
                                mEmail = "";
                            }
                            if (TextUtils.isEmpty(mEmail)) {
                                showSnackBar(llNext, getString(R.string.email_not_fetch));
                            } else {
                                hitSocialApi();// facebook signin
                            }
                            LoginManager.getInstance().logOut();
                        } else {
                            showInternetAlert(llNext);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "albums.fields(photos.fields(source),type,name,count),id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }


    private void signInGmail() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.w(TAG, "signInResult: code=" + account);
            mGoogleSignInClient.signOut();
            mEmail = account.getEmail();
            mName = account.getDisplayName();
            mImageUrl = String.valueOf(account.getPhotoUrl());
            mId = account.getId();
            hitSocialApi();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void hitSocialApi() {
        if (connectedToInternet(llNext)) {
            LoadingDialog.getLoader().showLoader(mContext);
            Call<UserModel> call = RetrofitClient.getInstance().social_login(
                    "1",
                    mId,
                    deviceToken,
                    platformStatus,
                    mName,
                    mEmail,
                    mImageUrl);
            call.enqueue(new retrofit2.Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    LoadingDialog.getLoader().dismissLoader();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {

                        setUserData(response.body());

                        if (utils.getInt(Const.PROFILE_STATUS, 0) == Const.PROFILE_IS_CREATED) {
                            Intent intent = new Intent(mContext, LandingActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Intent intent = new Intent(mContext, CreateProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(llNext, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                    LoadingDialog.getLoader().dismissLoader();
                    t.printStackTrace();
                }
            });
        }
    }


}
