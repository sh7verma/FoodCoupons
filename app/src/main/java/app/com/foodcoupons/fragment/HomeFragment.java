package app.com.foodcoupons.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.CategoryAdapter;
import app.com.foodcoupons.adapters.DealsAdapter;
import app.com.foodcoupons.adapters.TopDealsAdapter;
import app.com.foodcoupons.customviews.SnapViewHelper;
import app.com.foodcoupons.dialog.SearchFilterDialog;
import app.com.foodcoupons.dialog.SortPriceDialog;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.CategoryModel;
import app.com.foodcoupons.models.DealModel;
import app.com.foodcoupons.models.FilterModel;
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

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    static HomeFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_deal)
    RecyclerView rvDeal;

    @BindView(R.id.rv_top_deals)
    RecyclerView rvTopDeals;
    @BindView(R.id.ll_all_deals_filter)
    LinearLayout llAllDealsFilter;
    @BindView(R.id.txt_deals_filter)
    TextView txtDealsFilter;
    @BindView(R.id.txt_sort)
    TextView txtSort;
    @BindView(R.id.nested_scroll)
    NestedScrollView mNestedScrollView;

    DealsAdapter mDealsAdapter;
    CategoryAdapter mCategoryAdapter;
    TopDealsAdapter mTopDealAdapter;

    FilterSingletonModel mFilter;
    LinearLayoutManager mLayoutManager;

    ArrayList<DealModel.DealsBean> mDealList = new ArrayList<>();
    ArrayList<DealModel.DealsBean> mTopDealList = new ArrayList<>();
    ArrayList<CategoryModel.DataBean> mCategoryList = new ArrayList<>();
    private boolean loading = true;
    private int offSet = 0;
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("onReceive", "HomeFragment");
            mDealList = new ArrayList<>();
            hitDealsApi();
            hitTopApi();
            hitCategoryApi();
        }
    };

    public static HomeFragment newInstance(Context context) {
        fragment = new HomeFragment();
        mContext = context;
        return fragment;
    }

    void resetPaging() {
        mDealList = new ArrayList<>();
        offSet = 0;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onCreateStuff() {
        LocalBroadcastManager.getInstance(mContext).registerReceiver(receiver, new IntentFilter(Const.EXTRA));

        rvCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvDeal.setLayoutManager(mLayoutManager);

        rvTopDeals.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        new SnapViewHelper(rvTopDeals);

        setCategoryAdapter();
        setDealAdapter();
        setTopDealAdapter();

        mFilter = FilterSingletonModel.getInstance();
    }

    @Override
    protected void initListeners() {
        txtDealsFilter.setOnClickListener(this);
        txtSort.setOnClickListener(this);

        rvDeal.setNestedScrollingEnabled(false);
        rvCategory.setNestedScrollingEnabled(false);
        rvTopDeals.setNestedScrollingEnabled(false);

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        int visibleItemCount = mLayoutManager.getChildCount();
                        int totalItemCount = mLayoutManager.getItemCount();
                        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                        if (loading) {
                            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                                hitDealsApi();
                            }
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
        hitTopApi();
        hitCategoryApi();
        hitBrandsApi();
    }

    void hitDealsApi() {
        if (connectedToInternet(rvCategory)) {
            if (!TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {
                loading = false;
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
                        Const.TYPE_DEALS);

                call.enqueue(new Callback<DealModel>() {
                    @Override
                    public void onResponse(Call<DealModel> call, Response<DealModel> response) {
                        loading = true;
                        if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                            if (response.body().getDeals().size() > 0) {
                                offSet++;
                                mDealList.addAll(response.body().getDeals());
                                setDealAdapter();
                            }
                        } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                            showAlertSnackBar(rvCategory, response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<DealModel> call, Throwable t) {
                        loading = true;
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    void hitTopApi() {
        if (connectedToInternet(rvCategory)) {
            if (!TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {
                showProgress();
                Call<DealModel> call = RetrofitClient.getInstance().get_top_deal(utils.getString(Const.ACCESS_TOKEN, ""),
                        utils.getString(Const.LATITUDE, ""), utils.getString(Const.LONGITUDE, ""));
                call.enqueue(new Callback<DealModel>() {
                    @Override
                    public void onResponse(Call<DealModel> call, Response<DealModel> response) {
                        hideProgress();
                        if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                            mTopDealList = new ArrayList<>();
                            mTopDealList.addAll(response.body().getDeals());
                            setTopDealAdapter();
                        } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                            showAlertSnackBar(rvCategory, response.body().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<DealModel> call, Throwable t) {
                        hideProgress();
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    void hitBrandsApi() {
        if (mFilter.getBrands().size() == 0) {
            if (connectedToInternet(rvCategory)) {
                if (!TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {
                    Call<FilterModel> call = RetrofitClient.getInstance().get_brands(utils.getString(Const.ACCESS_TOKEN, ""));
                    call.enqueue(new Callback<FilterModel>() {
                        @Override
                        public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {
                            if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                                for (int i = 0; i < response.body().getBrands().size(); i++) {
                                    mFilter.getBrands().add(new FilterSingletonModel.BrandModel(
                                            response.body().getBrands().get(i).getName(),
                                            response.body().getBrands().get(i).getId(),
                                            response.body().getBrands().get(i).isSelected()));
                                }

                            } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                                showAlertSnackBar(rvCategory, response.body().getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<FilterModel> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        }
    }

    void hitCategoryApi() {
        if (connectedToInternet(rvCategory)) {
            showProgress();
            Call<CategoryModel> call = RetrofitClient.getInstance().get_category();
            call.enqueue(new Callback<CategoryModel>() {
                @Override
                public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                    hideProgress();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {

                        mCategoryList = new ArrayList<>();
                        mCategoryList.addAll(response.body().getData());

                        if (mFilter.getCuisine().size() == 0) {
                            for (int i = 0; i < mCategoryList.size(); i++) {
                                mFilter.getCuisine().add(
                                        new FilterSingletonModel.BrandModel(mCategoryList.get(i).getCategory(),
                                                mCategoryList.get(i).getId(), false));
                            }
                        }

                        setCategoryAdapter();
                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(rvCategory, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<CategoryModel> call, Throwable t) {
                    hideProgress();
                    t.printStackTrace();
                }
            });
        }
    }

    void setDealAdapter() {
//        if (mDealList.size() == 0) {
//            llAllDealsFilter.setVisibility(View.GONE);
//        } else {
//            llAllDealsFilter.setVisibility(View.VISIBLE);
//        }
        mDealsAdapter = new DealsAdapter(mContext, mDealList);
        rvDeal.setAdapter(mDealsAdapter);
    }

    void setTopDealAdapter() {
        mTopDealAdapter = new TopDealsAdapter(mContext, mTopDealList, mHeight);
        rvTopDeals.setAdapter(mTopDealAdapter);
    }

    void setCategoryAdapter() {
        mCategoryAdapter = new CategoryAdapter(mContext, mCategoryList, mHeight);
        rvCategory.setAdapter(mCategoryAdapter);
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
                        mDealList = new ArrayList<>();
                        hitDealsApi();
                    }
                });
                break;

            case R.id.txt_sort:
                new SortPriceDialog(mContext, new InterfacesCall.FilterApplied() {
                    @Override
                    public void onFilterApplied() {
                        mDealList = new ArrayList<>();
                        hitDealsApi();
                    }
                });
                break;

        }

    }
}
