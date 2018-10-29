package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.UserModel;
import app.com.foodcoupons.network.RetrofitClient;
import app.com.foodcoupons.utils.Const;
import app.com.foodcoupons.utils.LoadingDialog;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerifyActivity extends BaseActivity {

    public CountDownTimer mTimer;
    public long time;

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ed_otp)
    EditText edOtp;
    @BindView(R.id.txt_timer)
    TextView txtTimer;
    @BindView(R.id.txt_call)
    TextView txtCall;
    @BindView(R.id.txt_resend)
    TextView txtResend;
    @BindView(R.id.txt_phone)
    TextView txtPhone;
    @BindView(R.id.ll_call)
    LinearLayout llCall;
    @BindView(R.id.ll_resend)
    LinearLayout llResend;
    @BindView(R.id.ll_next)
    LinearLayout llNext;

    @Override
    protected int getContentView() {
        return R.layout.activity_otp_verify;
    }

    @Override
    protected void onCreateStuff() {
        txtPhone.setText(getIntent().getStringExtra(Const.COUNTRY_CODE) + " " + getIntent().getStringExtra(Const.PHONE_NO));
        disableButtons();
    }

    @Override
    protected void initUI() {
        edOtp.setTypeface(typefaceRegular);
    }

    @Override
    protected void initListener() {
        llResend.setOnClickListener(this);
        llNext.setOnClickListener(this);
        imgBack.setOnClickListener(this);

    }

    public void startTimer() {
        // final String FORMAT = "%02d:%02d";
        time = (1 * 60 * 1000);
        final String FORMAT = "%02d:%02d";
        mTimer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                time = millisUntilFinished;
                txtTimer.setText(String.format(
                        FORMAT,
                        TimeUnit.MILLISECONDS
                                .toMinutes(millisUntilFinished)
                                - TimeUnit.HOURS
                                .toMinutes(TimeUnit.MILLISECONDS
                                        .toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS
                                .toSeconds(millisUntilFinished)
                                - TimeUnit.MINUTES
                                .toSeconds(TimeUnit.MILLISECONDS
                                        .toMinutes(millisUntilFinished))));
            }

            @Override
            public void onFinish() {
                // TODO Auto-generated method stub
                enableButtons();
            }
        }.start();
    }

    private void enableButtons() {
        txtTimer.setText(R.string.Did_not_receive_an_OTP);
        txtTimer.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        txtTimer.setTypeface(typefaceRegular);
        llResend.setEnabled(true);

        txtCall.setBackground(getResources().getDrawable(R.drawable.grey_round_corner_stroke));
        txtResend.setBackground(getResources().getDrawable(R.drawable.grey_round_corner_stroke));

        txtCall.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        txtResend.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void disableButtons() {
        startTimer();
        txtTimer.setTypeface(typefaceSemiBold);
        txtTimer.setTextColor(getResources().getColor(R.color.black));

        llResend.setEnabled(false);

        txtCall.setBackground(getResources().getDrawable(R.drawable.light_grey_round_stroke));
        txtResend.setBackground(getResources().getDrawable(R.drawable.light_grey_round_stroke));

        txtCall.setTextColor(getResources().getColor(R.color.light_grey));
        txtResend.setTextColor(getResources().getColor(R.color.light_grey));
    }

    void checkOtp(UserModel response) {
        setUserData(response);
        if (utils.getInt(Const.PROFILE_STATUS, 0) == Const.PROFILE_IS_CREATED) {
            Intent intent = new Intent(mContext, LandingActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(mContext, CreateProfileActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_resend:
                disableButtons();
                hitResendApi();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.ll_next:
                if (!TextUtils.isEmpty(edOtp.getText().toString()) && edOtp.getText().toString().length() == 4)
                    hitApi();
                else
                    showAlertSnackBar(llCall, getString(R.string.enter_valid_otp));
                break;
        }
    }


    private void hitApi() {
        if (connectedToInternet(llNext)) {
            LoadingDialog.getLoader().showLoader(mContext);
            Call<UserModel> call = RetrofitClient.getInstance().verify_otp(utils.getString(Const.ACCESS_TOKEN, ""),
                    edOtp.getText().toString());

            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    LoadingDialog.getLoader().dismissLoader();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                        checkOtp(response.body());
                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(llCall, response.body().getMessage());
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

    private void hitResendApi() {
        edOtp.setText("");
        if (connectedToInternet(llNext)) {
            LoadingDialog.getLoader().showLoader(mContext);
            Call<UserModel> call = RetrofitClient.getInstance().resend_otp(utils.getString(Const.ACCESS_TOKEN, ""));
            call.enqueue(new Callback<UserModel>() {
                @Override
                public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                    LoadingDialog.getLoader().dismissLoader();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                        toast(response.body().getMessage());
                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(llCall, response.body().getMessage());
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
