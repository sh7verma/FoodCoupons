package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.NearByPlaceAdapter;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.GooglePlaceModal;
import app.com.foodcoupons.models.NearbyPlaceModel;
import app.com.foodcoupons.network.ApiInterface;
import app.com.foodcoupons.network.RetrofitClient;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by dev on 4/10/18.
 */

public class SearchLocationActivity extends BaseActivity implements InterfacesCall.AdapterItemSelected {

    @BindView(R.id.rv_place)
    RecyclerView rvPlace;
    @BindView(R.id.txt_recent_search)
    TextView txtRecentSearch;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.ll_current_location)
    LinearLayout llCurrentLocation;

    List<GooglePlaceModal.PredictionsBean> mPlaceArrayList = new ArrayList<>();

    private NearByPlaceAdapter mNearByPlaceAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_location;
    }

    @Override
    protected void onCreateStuff() {

    }

    @Override
    protected void initUI() {
        rvPlace.setLayoutManager(new LinearLayoutManager(mContext));
        mNearByPlaceAdapter = new NearByPlaceAdapter(mContext, mPlaceArrayList);
        rvPlace.setAdapter(mNearByPlaceAdapter);
    }

    @Override
    protected void initListener() {
        imgBack.setOnClickListener(this);
        llCurrentLocation.setOnClickListener(this);
        mNearByPlaceAdapter.onItemSelected(this);

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getPlaceAutoCompleteUrl(String.valueOf(charSequence));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                hideKeyboard(this);
                finish();
                break;
            case R.id.ll_current_location:
                hideKeyboard(this);
                Intent intent = new Intent();
                intent.putExtra(Const.TYPE, "0");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }

    }


    public void getPlaceAutoCompleteUrl(String input) {
        ApiInterface apiInterface = RetrofitClient.getAutoClient().create(ApiInterface.class);
        StringBuilder urlString = new StringBuilder();
        urlString.append("json?input=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&location=");
        urlString.append(utils.getString(Const.LONGITUDE, "") + "," + utils.getString(Const.LONGITUDE, "")); // append lat long of current location to show nearby results.
        urlString.append("&radius=500");
        urlString.append("&sensor=true");
        // urlString.append("&type=geocode");
        // urlString.append("&type=address");
        urlString.append("&language=en");
        urlString.append("&key=" + getString(R.string.mapKey));
        Log.d("Destination", "apiInter URL " + urlString.toString());
        Call<GooglePlaceModal> call = apiInterface.getGooglePlaces(urlString.toString());
        call.enqueue(new Callback<GooglePlaceModal>() {
            @Override
            public void onResponse(Call<GooglePlaceModal> call, retrofit2.Response<GooglePlaceModal> response) {
                Log.d("Destination", "apiInter response " + response.body().getPredictions().size());
                if (response.body().getPredictions().size() > 0) {
                    mPlaceArrayList = new ArrayList<>();
                    mPlaceArrayList = response.body().getPredictions();
                    mNearByPlaceAdapter = new NearByPlaceAdapter(mContext, mPlaceArrayList);
                    rvPlace.setAdapter(mNearByPlaceAdapter);
                    mNearByPlaceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GooglePlaceModal> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getPlaceNearbyPlaceUrl(String input) {

        // nearby service
        // https://maps.googleapis.com/maps/api/place/nearbysearch/json?
        // location=30.6577781,76.732713&radius=500&name=max&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4

        // autocomplete
        // https://maps.googleapis.com/maps/api/place/autocomplete/json?input=ncm+
        // &location=30.6577781,76.7327138&radius=1000&types=geocode&language=en&key=AIzaSyBAPTeUV-04HFtCIt3Ac8MtVFqim9CDlV4

        ApiInterface apiInterface = RetrofitClient.getClientNearBy().create(ApiInterface.class);
        StringBuilder urlString = new StringBuilder();
        urlString.append("json?keyword=");
        try {
            urlString.append(URLEncoder.encode(input, "utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        urlString.append("&limit=20");
        urlString.append("&location=");
        urlString.append(getIntent().getStringExtra(Const.LONGITUDE) + "," + getIntent().getStringExtra(Const.LONGITUDE)); // append lat long of current location to show nearby results.
        urlString.append("&radius=2000000");
        urlString.append("&sensor=true");
        urlString.append("&types=geocode");
        urlString.append("&language=en");
        urlString.append("&key=" + getString(R.string.mapKey));
        Log.d("Destination", "apiInter URL " + urlString.toString());
        Call<NearbyPlaceModel> call = apiInterface.getGoogleNearByPlaces(urlString.toString());

        call.enqueue(new Callback<NearbyPlaceModel>() {
            @Override
            public void onResponse(Call<NearbyPlaceModel> call, retrofit2.Response<NearbyPlaceModel> response) {
                Log.d("Destination", "apiInter response " + response.body().getResults().size());
                if (response.body().getResults().size() > 0) {
//                    mPlaceArrayList = new ArrayList<>();
//                    mPlaceArrayList = response.body().getResults();
//                    mNearByPlaceAdapter = new NearByPlaceAdapter(mContext, mPlaceArrayList);
//                    rvPlace.setMoreDealsAdapter(mNearByPlaceAdapter);
//                    mNearByPlaceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NearbyPlaceModel> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public void onAdapterItemSelected(int position) {
        hideKeyboard(this);
        Intent intent = new Intent();
        intent.putExtra(Const.TYPE, "1");
        intent.putExtra(Const.EXTRA, mPlaceArrayList.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}
