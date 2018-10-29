package app.com.foodcoupons.activities;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.DealsAdapter;
import app.com.foodcoupons.dialog.CategoryFilterDialog;
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

public class CategoryActivity extends BaseActivity {

    public FilterModel mFilter;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_sort)
    ImageView imgSort;
    @BindView(R.id.img_filter)
    ImageView imgFilter;
    @BindView(R.id.rv_deals)
    RecyclerView rvDeal;
    @BindView(R.id.ll_no_result)
    LinearLayout llNoResult;
    ArrayList<DealModel.DealsBean> mDealList = new ArrayList<>();
    DealsAdapter mDealsAdapter;
    LinearLayoutManager mLayoutManager;
    CategoryModel.DataBean mCategoryModel;

    private boolean loading = true;
    private int offSet = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_category;
    }

    @Override
    protected void onCreateStuff() {
        mCategoryModel = getIntent().getParcelableExtra(Const.EXTRA);

        txtTitle.setText(mCategoryModel.getCategory());
        mFilter = new FilterModel();

        setBrandsList();
        showProgress();
        hitDealsApi();
    }


    void setBrandsList() {

        List<FilterModel.BrandModel> brandlist = new ArrayList<>();
        for (int i = 0; i < FilterSingletonModel.getInstance().getBrands().size(); i++) {
            FilterModel.BrandModel model = new FilterModel.BrandModel(FilterSingletonModel.getInstance().getBrands().get(i).getName(),
                    FilterSingletonModel.getInstance().getBrands().get(i).getId(),
                    false);
            brandlist.add(model);
        }
        mFilter.setBrands(brandlist);
    }

    @Override
    protected void initUI() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvDeal.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void initListener() {

        imgBack.setOnClickListener(this);
        imgFilter.setOnClickListener(this);

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
    protected Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_filter:
                new CategoryFilterDialog(mContext, mFilter, new InterfacesCall.CategoryFilterApplied() {
                    @Override
                    public void onFilterApplied(FilterModel filterModel) {
                        mFilter = filterModel;
                        showProgress();
                        resetPaging();
                        hitDealsApi();
                    }

                    @Override
                    public void onClear(FilterModel filterModel) {
                        mFilter = filterModel;
                        setBrandsList();
                        showProgress();
                        resetPaging();
                        hitDealsApi();
                    }
                });
                break;
        }
    }

    void resetPaging() {
        offSet = 0;
        mDealList = new ArrayList<>();
    }

    void hitDealsApi() {
        if (connectedToInternet(txtTitle)) {
            if (!TextUtils.isEmpty(utils.getString(Const.LONGITUDE, ""))) {
                loading = false;
                Call<DealModel> call = RetrofitClient.getInstance().get_all_deal(utils.getString(Const.ACCESS_TOKEN, ""),
                        utils.getString(Const.LATITUDE, ""),
                        utils.getString(Const.LONGITUDE, ""),
                        mFilter.getSortPrice(),
                        "",
                        getBrandSelected(),
                        getMaxPricePriceSelected(),
                        getMinPricePriceSelected(),
                        String.valueOf(offSet),
                        String.valueOf(mCategoryModel.getId()),
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
                            }
                            setDealAdapter();

                        } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                            showAlertSnackBar(txtTitle, response.body().getMessage());
                            setDealAdapter();
                        }
                    }

                    @Override
                    public void onFailure(Call<DealModel> call, Throwable t) {
                        loading = true;
                        t.printStackTrace();
                        setDealAdapter();
                    }
                });
            }
        }
    }

    public String getMaxPricePriceSelected() {
        String maxPrice = "";
        for (int i = 0; i < mFilter.getPrice().size(); i++) {
            if (mFilter.getPrice().get(i).isSelected()) {
                maxPrice = mFilter.getPrice().get(i).getMax();
            }
        }
        return maxPrice;
    }

    public String getMinPricePriceSelected() {
        String min = "";
        for (int i = 0; i < mFilter.getPrice().size(); i++) {
            if (mFilter.getPrice().get(i).isSelected()) {
                min = mFilter.getPrice().get(i).getMin();
            }
        }
        return min;
    }

    public String getBrandSelected() {
        StringBuilder brandIds = new StringBuilder();
        for (int i = 0; i < mFilter.getBrands().size(); i++) {
            if (mFilter.getBrands().get(i).isSelected()) {
                brandIds.append(mFilter.getBrands().get(i).getId() + ",");
            }
        }
        if (brandIds.toString().endsWith(",")) {
            return brandIds.toString().substring(0, brandIds.toString().length() - 1).trim();
        }
        return brandIds.toString().trim();

    }

    void setDealAdapter() {
        hideProgress();
        if (mDealList.size() > 0) {
            llNoResult.setVisibility(View.GONE);
        } else {
            llNoResult.setVisibility(View.VISIBLE);
        }
        mDealsAdapter = new DealsAdapter(mContext, mDealList);
        rvDeal.setAdapter(mDealsAdapter);
    }

}
