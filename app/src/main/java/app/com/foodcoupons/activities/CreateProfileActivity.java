package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

import app.com.foodcoupons.R;
import app.com.foodcoupons.customviews.RoundedTransformation;
import app.com.foodcoupons.customviews.ViewImageActivity;
import app.com.foodcoupons.models.UserModel;
import app.com.foodcoupons.network.RetrofitClient;
import app.com.foodcoupons.utils.Connection_Detector;
import app.com.foodcoupons.utils.Const;
import app.com.foodcoupons.utils.LoadingDialog;
import app.com.foodcoupons.utils.OptionPhotoSelection;
import app.com.foodcoupons.utils.Validations;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Response;

public class CreateProfileActivity extends BaseActivity {

    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.ed_email)
    EditText edEmail;
    @BindView(R.id.img_done)
    ImageView ImgDone;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.img_terms)
    ImageView imgTerms;
    @BindView(R.id.txt_terms)
    TextView txtTerms;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    boolean terms = false;

    private File pathImageFile = null;
    private String imagePath = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_create_profile;
    }

    @Override
    protected void onCreateStuff() {
//        Spannable spanText = new SpannableString(getString(R.string.pls_accept_terms));
//        spanText.setSpan(new ForegroundColorSpan(Color.LTGRAY), 31, 59, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        txtTerms.setText(spanText);
        setData();
    }

    @Override
    protected void initUI() {
        edName.setTypeface(typefaceBold);
        edEmail.setTypeface(typefaceBold);

        if (utils.getInt(Const.LOGIN_VIA, 0) == Const.SOCIAL_LOGIN) {
            edName.setFocusableInTouchMode(false);
            edEmail.setFocusableInTouchMode(false);
        }

    }

    @Override
    protected void initListener() {
        imgAdd.setOnClickListener(this);
        ImgDone.setOnClickListener(this);
        imgTerms.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_add:
                intent = new Intent(this, OptionPhotoSelection.class);
                if (TextUtils.isEmpty(imagePath)) {
                    intent.putExtra("type", "1");// for add photo
                } else {
                    intent.putExtra("type", "2");// for view or delete photo
                }
                startActivityForResult(intent, Const.REQ_PIC);
                break;
            case R.id.img_done:
                if (!terms) {
                    showAlertSnackBar(ImgDone, getString(R.string.pls_accept_terms));
                    return;
                }
                if (Validations.checkNameValidation(mContext, edName) &&
                        Validations.checkEmailValidation(mContext, edEmail)) {
                    hitApi();
                }
                break;
            case R.id.img_terms:
                if (terms) {
                    terms = false;
                } else {
                    terms = true;
                }
                changeTermsView();
                break;
        }
    }

    void changeTermsView() {
        if (terms) {
            imgTerms.setImageResource(R.mipmap.ic_brand_btn_s);
        } else {
            imgTerms.setImageResource(R.mipmap.ic_brand_btn);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Const.REQ_PIC:
                    if ((new Connection_Detector(this)).isConnectingToInternet()) {
                        if (data.getStringExtra("filePath").equalsIgnoreCase("null")) {
                            pathImageFile = null;
                            showImage(pathImageFile);

                        } else if (data.getStringExtra("filePath").equalsIgnoreCase("show")) {
                            String picValue = "";
                            if (pathImageFile != null) {
                                picValue = imagePath;
                            } else {
                                picValue = utils.getString(Const.PROFILE_PIC, "");
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                ActivityOptionsCompat option = ActivityOptionsCompat
                                        .makeSceneTransitionAnimation(CreateProfileActivity.this, imgProfile, "full_imageview");
                                Intent in = new Intent(CreateProfileActivity.this, ViewImageActivity.class);
                                in.putExtra("display", "" + picValue);
                                startActivity(in, option.toBundle());
                            } else {
                                Intent in = new Intent(CreateProfileActivity.this, ViewImageActivity.class);
                                in.putExtra("display", "" + picValue);
                                startActivity(in);
                                overridePendingTransition(0, 0);
                            }
                        } else {
//                            imagePath = data.getStringExtra("filePath");
                            Log.e("IMage Path = ", data.getStringExtra("filePath"));
                            pathImageFile = new File(data.getStringExtra("filePath"));
                            showImage(pathImageFile);
                        }
                    } else
                        showInternetAlert(ImgDone);
                    break;

            }
        }
    }


    private void setData() {
        changeTermsView();

        if (!TextUtils.isEmpty(utils.getString(Const.NAME, ""))) {
            edName.setText(utils.getString(Const.NAME, "").trim());
        }
        if (!TextUtils.isEmpty(utils.getString(Const.EMAIL, ""))) {
            edEmail.setText(utils.getString(Const.EMAIL, "").trim());
        }
        if (!TextUtils.isEmpty(utils.getString(Const.PROFILE_PIC, ""))) {
            imagePath = utils.getString(Const.PROFILE_PIC, "");

            progressBar.setVisibility(View.VISIBLE);
            imgAdd.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(imagePath)) {
            Picasso.get()
                    .load(imagePath)
                    .transform(new RoundedTransformation((int) (mHeight * 0.01), 0))
                    .resize((int) (mHeight * 0.185), (int) (mHeight * 0.185))
                    .placeholder(R.mipmap.ic_user)
                    .centerCrop()
                    .error(R.mipmap.ic_user).into(imgProfile, new Callback() {
                @Override
                public void onSuccess() {
                    Log.w(TAG, "onError:Sucess code=");
                    progressBar.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.VISIBLE);

                    imgAdd.setImageDrawable(getResources().getDrawable(R.mipmap.ic_edit_photo));
                }

                @Override
                public void onError(Exception e) {
                    Log.w(TAG, "onError:failed code=" + e.getMessage());
                    progressBar.setVisibility(View.GONE);
                    imgAdd.setVisibility(View.VISIBLE);
                    imgAdd.setImageDrawable(getResources().getDrawable(R.mipmap.ic_plus_home));
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
            imgAdd.setVisibility(View.VISIBLE);
            Picasso.get()
                    .load(R.mipmap.ic_user)
                    .into(imgProfile);
            imgAdd.setImageDrawable(getResources().getDrawable(R.mipmap.ic_plus_home));
        }
    }

    void showImage(File file) {
        if (file != null) {
            Picasso.get()
                    .load(file)
                    .transform(new RoundedTransformation((int) (mHeight * 0.015), 0))
                    .resize((int) (mHeight * 0.185), (int) (mHeight * 0.185))
                    .placeholder(R.mipmap.ic_user)
                    .centerCrop(Gravity.TOP)
                    .error(R.mipmap.ic_user).into(imgProfile, new Callback() {
                @Override
                public void onSuccess() {
                    Log.w(TAG, "onError:Sucess code=");
                    imgAdd.setImageDrawable(getResources().getDrawable(R.mipmap.ic_edit_photo));
                }

                @Override
                public void onError(Exception e) {
                    Log.w(TAG, "onError:failed code=" + e.getMessage());
                }
            });

        } else {
            Picasso.get()
                    .load(R.mipmap.ic_user)
                    .into(imgProfile);
        }
    }


    private void hitApi() {
        if (connectedToInternet(ImgDone)) {
            LoadingDialog.getLoader().showLoader(mContext);
            Call<UserModel> call = RetrofitClient.getInstance().create_profile(
                    createPartFromString(edName.getText().toString().trim()),
                    createPartFromString(utils.getString(Const.ACCESS_TOKEN, "")),
                    createPartFromString(edEmail.getText().toString().trim()),
                    createFilePart(pathImageFile, "profile_pic"));
            call.enqueue(new retrofit2.Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    LoadingDialog.getLoader().dismissLoader();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                        setUserData(response.body());
                        try {
                            if (pathImageFile != null) {
                                pathImageFile.delete();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Intent intent = new Intent(mContext, LandingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(txtTerms, response.body().getMessage());
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
