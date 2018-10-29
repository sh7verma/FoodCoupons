package app.com.foodcoupons.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.com.foodcoupons.R;


public class UpdateApp extends AppCompatActivity {

    Utils util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        util = new Utils(getApplicationContext());

        AlertDialog.Builder build = new AlertDialog.Builder(UpdateApp.this);
        build.setCancelable(false);
        build.setMessage(getString(R.string.new_version));
        build.setPositiveButton(getString(R.string.update),
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent aboutIntent = new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://play.google.com/store/apps/details?id=com.app.stringmessenger"));
                            startActivity(aboutIntent);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                });
        if (util.getString("forse_update", "0").equals("0")) {
            build.setNegativeButton(getString(R.string.cancel),
                    new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            finish();
                        }
                    });
        }
        build.show();
    }
}