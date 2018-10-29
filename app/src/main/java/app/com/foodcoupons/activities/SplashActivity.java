package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.com.foodcoupons.R;
import app.com.foodcoupons.utils.Const;


public class SplashActivity extends BaseActivity {

    private int TIME_OUT = 500;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreateStuff() {
        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                openActivity();
            }
        }, TIME_OUT);
    }

    @Override
    protected void initUI() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "app.com.foodcoupons",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected Context getContext() {
        return this;
    }


    @Override
    public void onClick(View view) {

    }

    void openActivity() {

        if (utils.getInt(Const.INTRO, 0) == 0) {

            Intent in = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(in);
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);

        } else if (utils.getInt(Const.PROFILE_STATUS, 0) != Const.PROFILE_IS_CREATED) {

            Intent in = new Intent(SplashActivity.this, EnterNumberActivity.class);
            startActivity(in);
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);


        } else if (utils.getInt(Const.PROFILE_STATUS, 0) == Const.PROFILE_IS_CREATED) {
            Intent intent = new Intent(this, LandingActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
        }
    }

}
