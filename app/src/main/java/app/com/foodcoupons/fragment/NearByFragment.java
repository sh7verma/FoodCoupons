package app.com.foodcoupons.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.DealsAdapter;
import app.com.foodcoupons.dialog.SearchFilterDialog;
import app.com.foodcoupons.dialog.SortPriceDialog;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.DealModel;
import app.com.foodcoupons.models.FilterSingletonModel;
import app.com.foodcoupons.network.RetrofitClient;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 28/9/18.
 */

public class NearByFragment extends BaseFragment implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    static NearByFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;
    @BindView(R.id.rv_deal)
    RecyclerView rvDeal;
    @BindView(R.id.txt_deals_filter)
    TextView txtDealsFilter;
    @BindView(R.id.txt_sort)
    TextView txtSort;

    DealsAdapter mDealsAdapter;
    List<DealModel.DealsBean> mDealList = new ArrayList<>();
    FilterSingletonModel mFilter;
    LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    private int offSet = 0;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("onReceive", "NearByFragment");
            resetPaging();
            hitDealsApi();
        }
    };

    public static NearByFragment newInstance(Context context) {
        fragment = new NearByFragment();
        mContext = context;
        return fragment;
    }

    void resetPaging() {
        mDealList = new ArrayList<>();
        offSet = 0;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void onCreateStuff() {
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, new IntentFilter(Const.EXTRA));

        mFilter = FilterSingletonModel.getInstance();
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeal.setLayoutManager(mLayoutManager);
    }

    void setDealsAdapter() {
        mDealsAdapter = new DealsAdapter(mContext, mDealList);
        rvDeal.setAdapter(mDealsAdapter);
    }

    @Override
    protected void initListeners() {
        txtDealsFilter.setOnClickListener(this);
        txtSort.setOnClickListener(this);

        rvDeal.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0) {
                            hitDealsApi();
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        resetPaging();
        hitDealsApi();
    }

    void hitDealsApi() {
        if (connectedToInternet()) {
            if (!TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {
                Call<DealModel> call = RetrofitClient.getInstance().get_all_deal(utils.getString(Const.ACCESS_TOKEN, ""),
                        utils.getString(Const.LATITUDE, ""),
                        utils.getString(Const.LONGITUDE, ""),
                        mFilter.getSortPrice(),
                        "",
                        mFilter.getBrandSelected(),
                        mFilter.getMaxPricePriceSelected(),
                        mFilter.getMinPricePriceSelected(),
                        String.valueOf(offSet),
                        mFilter.getCuisineSelected(),
                        "",
                        Const.TYPE_NEAR_BY_DEALS);

                call.enqueue(new Callback<DealModel>() {
                    @Override
                    public void onResponse(Call<DealModel> call, Response<DealModel> response) {
                        loading = true;
                        if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                            if (response.body().getDeals().size() > 0) {
                                offSet++;
                                mDealList.addAll(response.body().getDeals());
                                setDealsAdapter();
                            }

                        } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                            showAlertSnackBar(rvDeal, response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<DealModel> call, Throwable t) {
                        t.printStackTrace();
                        loading = true;
                    }

                });
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(receiver);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_deals_filter:
                new SearchFilterDialog(mContext, new InterfacesCall.FilterApplied() {
                    @Override
                    public void onFilterApplied() {
                        resetPaging();
                        hitDealsApi();
                    }
                });
                break;

            case R.id.txt_sort:
                new SortPriceDialog(mContext, new InterfacesCall.FilterApplied() {
                    @Override
                    public void onFilterApplied() {
                        resetPaging();
                        hitDealsApi();
                    }
                });
                break;

        }

    }

}
