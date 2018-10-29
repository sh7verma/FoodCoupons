package app.com.foodcoupons.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.com.foodcoupons.R;
import app.com.foodcoupons.customviews.RoundedTransformation;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;

/**
 * Created by dev on 28/9/18.
 */

public class ProfileFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static ProfileFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.txt_mobile)
    TextView txtMobile;

    public static ProfileFragment newInstance(Context context) {
        fragment = new ProfileFragment();
        mContext = context;
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_profile;

    }

    @Override
    protected void onCreateStuff() {
        setData();
    }


    private void setData() {
        String imagePath = "";

        if (!TextUtils.isEmpty(utils.getString(Const.NAME, ""))) {
            txtName.setText(utils.getString(Const.NAME, "").trim());
        }

        txtMobile.setText(utils.getString(Const.COUNTRY_CODE, "").trim() + " " + utils.getString(Const.PHONE_NO, "").trim());

        if (!TextUtils.isEmpty(utils.getString(Const.EMAIL, ""))) {
            txtEmail.setText(utils.getString(Const.EMAIL, "").trim());
            txtEmail.setVisibility(View.VISIBLE);
        } else {
            txtEmail.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(utils.getString(Const.PROFILE_PIC, ""))) {
            imagePath = utils.getString(Const.PROFILE_PIC, "");
        }

        if (!TextUtils.isEmpty(imagePath)) {
            Picasso.get()
                    .load(imagePath)
                    .transform(new RoundedTransformation((int) (mHeight * 0.01), 0))
                    .resize((int) (mHeight * 0.185), (int) (mHeight * 0.185))
                    .placeholder(R.mipmap.ic_profile_s)
                    .centerCrop()
                    .error(R.mipmap.ic_profile_s).into(imgProfile, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError(Exception e) {
                }
            });
        } else {
            Picasso.get()
                    .load(R.mipmap.ic_profile_s)
                    .into(imgProfile);
        }
    }

    @Override
    protected void initListeners() {

    }
}
