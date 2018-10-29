package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.LandingPagerAdapter;
import app.com.foodcoupons.customviews.CustomViewPager;
import app.com.foodcoupons.fragment.BrochuresFragment;
import app.com.foodcoupons.fragment.HomeFragment;
import app.com.foodcoupons.fragment.NearByFragment;
import app.com.foodcoupons.fragment.ProfileFragment;
import app.com.foodcoupons.helper.GoogleMapInitiate;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.GooglePlaceModal;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;


public class LandingActivity extends BaseActivity implements InterfacesCall.MapInterface {

    private static final int LOCATION = 1;
    public static InterfacesCall.LocationInterface locationInterface;

    @BindView(R.id.vp_landing)
    CustomViewPager vpLanding;
    @BindView(R.id.rl_location)
    RelativeLayout rlLocation;

    LocalBroadcastManager mBroadcastManager;

    // bottom bar
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.ll_nearby)
    LinearLayout llNearby;
    @BindView(R.id.ll_brochures)
    LinearLayout llBrochures;
    @BindView(R.id.ll_profile)
    LinearLayout llProfile;
    @BindView(R.id.img_home)
    ImageView imgHome;
    @BindView(R.id.img_nearby)
    ImageView imgNearby;
    @BindView(R.id.img_brochures)
    ImageView imgBrochures;
    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.txt_home)
    TextView txtHome;
    @BindView(R.id.txt_nearby)
    TextView txtNearby;
    @BindView(R.id.txt_brochures)
    TextView txtBrochures;
    @BindView(R.id.txt_profile)
    TextView txtProfile;
    @BindView(R.id.txt_loc)
    TextView txtLocation;
    @BindView(R.id.ll_location)
    LinearLayout llLocation;

    // Current fragment selected
    int mViewSelection = Const.FRAG_NULL;
    Location mCurrentLocation;
    GoogleMapInitiate mapInitiate;
    LandingPagerAdapter mAdapter;

    public static void setInterface(InterfacesCall.LocationInterface location) {
        locationInterface = location;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_landing;
    }

    @Override
    protected void initUI() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationInterface.onresume();
        GoogleMapInitiate.setInterface(this);
        hideKeyboard(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationInterface.onpause();
    }


    @Override
    protected void onCreateStuff() {
        mBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        vpLanding.setPagingEnabled(false);
        mapInitiate = new GoogleMapInitiate(this, null);

        if (!TextUtils.isEmpty(utils.getString(Const.ADDRESS, ""))) {
            txtLocation.setText(utils.getString(Const.ADDRESS, ""));
        }

        loadFragment(Const.FRAG_HOME);

        mAdapter = new LandingPagerAdapter(getSupportFragmentManager());
        mAdapter.addFragment(HomeFragment.newInstance(this));
        mAdapter.addFragment(NearByFragment.newInstance(this));
        mAdapter.addFragment(BrochuresFragment.newInstance(this));
        mAdapter.addFragment(ProfileFragment.newInstance(this));

        vpLanding.setAdapter(mAdapter);
        vpLanding.setOffscreenPageLimit(4);

    }

    @Override
    protected void initListener() {
        llHome.setOnClickListener(this);
        llNearby.setOnClickListener(this);
        llBrochures.setOnClickListener(this);
        llProfile.setOnClickListener(this);
        llLocation.setOnClickListener(this);
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void loadFragment(int selected) {
        hideKeyboard(this);

        rlLocation.setVisibility(View.VISIBLE);

        imgHome.setImageResource(R.mipmap.ic_home);
        txtHome.setTextColor(getResources().getColor(R.color.grey_text));

        imgNearby.setImageResource(R.mipmap.ic_nearby);
        txtNearby.setTextColor(getResources().getColor(R.color.grey_text));

        imgBrochures.setImageResource(R.mipmap.ic_brouchers);
        txtBrochures.setTextColor(getResources().getColor(R.color.grey_text));

        imgProfile.setImageResource(R.mipmap.ic_profile);
        txtProfile.setTextColor(getResources().getColor(R.color.grey_text));

        if (selected == Const.FRAG_HOME) {
            imgHome.setImageResource(R.mipmap.ic_home_a);
            txtHome.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (selected == Const.FRAG_NEARBY) {
            imgNearby.setImageResource(R.mipmap.ic_nearby_a);
            txtNearby.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (selected == Const.FRAG_BROCHURES) {
            imgBrochures.setImageResource(R.mipmap.ic_brouchers_a);
            txtBrochures.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else if (selected == Const.FRAG_PROFILE) {
            rlLocation.setVisibility(View.GONE);
            imgProfile.setImageResource(R.mipmap.ic_profile_s);
            txtProfile.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        if (mViewSelection != selected) {
            mViewSelection = selected;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // bottom bar
            case R.id.ll_home:
                loadFragment(Const.FRAG_HOME);
                vpLanding.setCurrentItem(Const.FRAG_HOME);

                break;
            case R.id.ll_nearby:
                loadFragment(Const.FRAG_NEARBY);
                vpLanding.setCurrentItem(Const.FRAG_NEARBY);

                break;
            case R.id.ll_brochures:
                loadFragment(Const.FRAG_BROCHURES);
                vpLanding.setCurrentItem(Const.FRAG_BROCHURES);

                break;
            case R.id.ll_profile:
                loadFragment(Const.FRAG_PROFILE);
                vpLanding.setCurrentItem(Const.FRAG_PROFILE);
                break;

            case R.id.ll_location:
                if (!TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {
                    Intent intent = new Intent(mContext, SearchLocationActivity.class);
                    startActivityForResult(intent, LOCATION);
                } else {
                    showSnackBar(llHome, getString(R.string.fetch_location));
                }
                break;
        }
    }

    @Override
    public void onLocationUpdate(LocationResult location) {
        Log.e("LastLocation", String.valueOf(location.getLastLocation()));
        if (mCurrentLocation == null && TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {

            mCurrentLocation = location.getLastLocation();

            utils.setString(Const.LATITUDE, String.valueOf(mCurrentLocation.getLatitude()));
            utils.setString(Const.LONGITUDE, String.valueOf(mCurrentLocation.getLongitude()));

            txtLocation.setText(mapInitiate.getAddress(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));

            utils.setString(Const.ADDRESS, txtLocation.getText().toString().trim());

            mBroadcastManager.sendBroadcast(new Intent(Const.EXTRA));

        } else {
            locationInterface.onpause();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case LOCATION:
                    if (data.getStringExtra(Const.TYPE).equals("0")) {
                        utils.setString(Const.LATITUDE, "");
                        utils.setString(Const.LONGITUDE, "");
                        mCurrentLocation = null;
                        locationInterface.onresume();

                    } else if (data.getStringExtra(Const.TYPE).equals("1")) {
                        GooglePlaceModal.PredictionsBean mModel;
                        mModel = data.getParcelableExtra(Const.EXTRA);
                        txtLocation.setText(mModel.getDescription());

                        utils.setString(Const.ADDRESS, txtLocation.getText().toString().trim());

                        mapInitiate.getLocationByPlaceId(mModel.getPlace_id(), new InterfacesCall.GetPlaceById() {
                            @Override
                            public void onGetPlace(LatLng latLng) {
                                utils.setString(Const.LATITUDE, String.valueOf(latLng.latitude));
                                utils.setString(Const.LONGITUDE, String.valueOf(latLng.longitude));
                                mBroadcastManager.sendBroadcast(new Intent(Const.EXTRA));
                            }
                        });
                    }
                    break;
            }
        }
    }
}
