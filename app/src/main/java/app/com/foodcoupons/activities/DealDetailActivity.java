package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
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
import app.com.foodcoupons.adapters.DealImagePagerAdapter;
import app.com.foodcoupons.adapters.DealsAdapter;
import app.com.foodcoupons.customviews.CirclePageIndicator;
import app.com.foodcoupons.models.DealModel;
import app.com.foodcoupons.network.RetrofitClient;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dev on 9/10/18.
 */

public class DealDetailActivity extends BaseActivity {


    @BindView(R.id.cp_indicator)
    CirclePageIndicator cpIndicator;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.txt_deal_price)
    TextView txtDealPrice;
    @BindView(R.id.txt_coupon_valid)
    TextView txt_coupon_valid;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_discription)
    TextView txtDiscription;
    @BindView(R.id.txt_discounted_price)
    TextView txtDiscountedPrice;
    @BindView(R.id.txt_original_price)
    TextView txtOriginalPrice;
    @BindView(R.id.txt_offer_detail)
    TextView txtOfferDetail;
    @BindView(R.id.txt_location)
    TextView txtLocation;
    @BindView(R.id.txt_navigation)
    TextView txtNavigation;
    @BindView(R.id.txt_call)
    TextView txtCall;
    @BindView(R.id.txt_buy_price)
    TextView txtBuyPrice;


    @BindView(R.id.txt_offer_description)
    TextView txtOfferDes;
    @BindView(R.id.txt_term_and_condition)
    TextView txtTerm;
    @BindView(R.id.txt_redem_instruction)
    TextView txtRedem;

    @BindView(R.id.img_offer_description)
    ImageView imgOfferDes;
    @BindView(R.id.img_term_and_condition)
    ImageView imgTerm;
    @BindView(R.id.img_redem_instruction)
    ImageView imgRedem;

    @BindView(R.id.ll_offer_description)
    LinearLayout llOfferDes;
    @BindView(R.id.ll_term_and_condition)
    LinearLayout llTerm;
    @BindView(R.id.ll_redem_instruction)
    LinearLayout llRedem;


    @BindView(R.id.vp_get_deal)
    ViewPager vpImages;
    @BindView(R.id.rv_get_deal)
    RecyclerView rvMoreDeal;
    @BindView(R.id.txt_more_deals)
    TextView txtMoreDeals;
    DealsAdapter dealsAdapter;
    DealImagePagerAdapter adapter;
    DealModel.DealsBean mDealModel;

    @BindView(R.id.nested_scroll)
    NestedScrollView mNestedScrollView;

    private int mCurrentOpened = 0;

    private List<DealModel.DealsBean> mDealList = new ArrayList<>();
    private ArrayList<DealModel.DealsBean.ImagesBean> mDealImageArray = new ArrayList<>();
    private LinearLayoutManager mLayoutManager;


    private boolean loading = true;
    private int offSet = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_deal_detail;
    }

    @Override
    protected void initUI() {
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvMoreDeal.setLayoutManager(mLayoutManager);
    }

    @Override
    protected void onCreateStuff() {
        mDealModel = getIntent().getParcelableExtra(Const.EXTRA);

        setData();
        hitApi();
        hitMoreDealsApi();
    }

    void setData() {
        if (mDealModel.getDeal_images() != null) {
            mDealImageArray.addAll(mDealModel.getDeal_images());
            if (!TextUtils.isEmpty(String.valueOf(mDealModel.getImage()))) {
                mDealImageArray.remove(1);
            }
        } else {

            DealModel.DealsBean.ImagesBean imagesBean = new DealModel.DealsBean.ImagesBean();
            imagesBean.setImage(mDealModel.getImage());

            mDealImageArray.add(imagesBean);
        }


        if (!TextUtils.isEmpty(String.valueOf(mDealModel.getDeal_price()))) {
            txtDealPrice.setText(Const.CURRENCY + mDealModel.getDeal_price());
            txtBuyPrice.setText(Const.CURRENCY + mDealModel.getDeal_price());
        }
        if (!TextUtils.isEmpty(String.valueOf(mDealModel.getDiscounted_price())))
            txtDiscountedPrice.setText(Const.CURRENCY + mDealModel.getDiscounted_price());

        if (!TextUtils.isEmpty(String.valueOf(mDealModel.getOrignal_price()))) {
            txtOriginalPrice.setText(Const.CURRENCY + mDealModel.getOrignal_price());
            txtOriginalPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        if (!TextUtils.isEmpty(mDealModel.getLocation()))
            txtLocation.setText(mDealModel.getLocation());

        if (!TextUtils.isEmpty(mDealModel.getOffer_detail()))
            txtOfferDetail.setText(mDealModel.getOffer_detail());

        if (!TextUtils.isEmpty(mDealModel.getDescription())) {
            txtDiscription.setText(mDealModel.getDescription());
        }

        if (!TextUtils.isEmpty(mDealModel.getOffer_description())) {
            txtOfferDes.setText(mDealModel.getOffer_description());
        }
        if (!TextUtils.isEmpty(mDealModel.getTerms_condition())) {
            txtTerm.setText(mDealModel.getTerms_condition());
        }
        if (!TextUtils.isEmpty(mDealModel.getRedemption_instruction())) {
            txtRedem.setText(mDealModel.getRedemption_instruction());
        }

        if (!TextUtils.isEmpty(mDealModel.getDeal_name()))
            txtName.setText(mDealModel.getDeal_name());
        if (!TextUtils.isEmpty(mDealModel.getValid_upto()))
            txt_coupon_valid.setText(getString(R.string.coupon_valid_til) + " " +
                    utils.timeFormatChange(mDealModel.getValid_upto()));

        adapter = new DealImagePagerAdapter(mContext, mDealImageArray);
        vpImages.setAdapter(adapter);
        cpIndicator.setViewPager(vpImages);

    }

    void setMoreDealsAdapter() {
        if (mDealList.size() > 0) {
            txtMoreDeals.setVisibility(View.VISIBLE);
        } else {
            txtMoreDeals.setVisibility(View.GONE);
        }
        dealsAdapter = new DealsAdapter(mContext, mDealList);
        rvMoreDeal.setAdapter(dealsAdapter);
    }

    @Override
    protected void initListener() {
        llOfferDes.setOnClickListener(this);
        llTerm.setOnClickListener(this);
        llRedem.setOnClickListener(this);
        txtNavigation.setOnClickListener(this);
        imgBack.setOnClickListener(this);

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
                                hitMoreDealsApi();
                            }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_offer_description:
                openDetailView(1);
                break;
            case R.id.ll_term_and_condition:
                openDetailView(2);
                break;
            case R.id.ll_redem_instruction:
                openDetailView(3);
                break;
            case R.id.txt_navigation:
//                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + mDealModel.getLatitude() + "," + mDealModel.getLocation());
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }
                Intent intent;
                String path = "http://maps.google.com/maps?saddr="
                        + utils.getString(Const.LATITUDE, "") + ","
                        + utils.getString(Const.LONGITUDE, "") + "&daddr="
                        + mDealModel.getLatitude() + ","
                        + mDealModel.getLongitude();


                intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(path));
                startActivity(intent);

                break;
            case R.id.img_back:
                finish();
                break;

        }
    }

    private void openDetailView(int viewClicked) {
        if (viewClicked == 1) {
            if (mCurrentOpened != 0 && mCurrentOpened != 1) {
                HideDetailView();
                txtOfferDes.setVisibility(View.VISIBLE);
                mCurrentOpened = 1;
                imgOfferDes.setImageResource(R.mipmap.ic_close_det);
            } else if (mCurrentOpened == 1) {
                HideDetailView();
                mCurrentOpened = 0;
            } else {
                txtOfferDes.setVisibility(View.VISIBLE);
                mCurrentOpened = 1;
                imgOfferDes.setImageResource(R.mipmap.ic_close_det);
            }
        } else if (viewClicked == 2) {
            if (mCurrentOpened != 0 && mCurrentOpened != 2) {
                HideDetailView();
                txtTerm.setVisibility(View.VISIBLE);
                mCurrentOpened = 2;
                imgTerm.setImageResource(R.mipmap.ic_close_det);
            } else if (mCurrentOpened == 2) {
                HideDetailView();
                mCurrentOpened = 0;
            } else {
                txtTerm.setVisibility(View.VISIBLE);
                mCurrentOpened = 2;
                imgTerm.setImageResource(R.mipmap.ic_close_det);
            }
        } else if (viewClicked == 3) {
            if (mCurrentOpened != 0 && mCurrentOpened != 3) {
                HideDetailView();
                txtRedem.setVisibility(View.VISIBLE);
                mCurrentOpened = 3;
                imgRedem.setImageResource(R.mipmap.ic_close_det);
            } else if (mCurrentOpened == 3) {
                HideDetailView();
                mCurrentOpened = 0;
            } else {
                txtRedem.setVisibility(View.VISIBLE);
                mCurrentOpened = 3;
                imgRedem.setImageResource(R.mipmap.ic_close_det);
            }
        }
    }

    private void HideDetailView() {
        if (mCurrentOpened == 1) {
            txtOfferDes.setVisibility(View.GONE);
            imgOfferDes.setImageResource(R.mipmap.ic_expand_det);
        } else if (mCurrentOpened == 2) {
            txtTerm.setVisibility(View.GONE);
            imgTerm.setImageResource(R.mipmap.ic_expand_det);
        } else if (mCurrentOpened == 3) {
            txtRedem.setVisibility(View.GONE);
            imgRedem.setImageResource(R.mipmap.ic_expand_det);
        }
    }

    void hitApi() {
        if (connectedToInternet(rvMoreDeal)) {
            showProgress();
            Call<DealModel> call = RetrofitClient.getInstance().deal_detail_by_id(
                    utils.getString(Const.ACCESS_TOKEN, ""),
                    String.valueOf(mDealModel.getId()));
            call.enqueue(new Callback<DealModel>() {
                @Override
                public void onResponse(Call<DealModel> call, Response<DealModel> response) {
                    hideProgress();
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                        mDealModel = response.body().getDeal_detail();
                        setData();
                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(rvMoreDeal, response.body().getMessage());
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

    void hitMoreDealsApi() {
        if (connectedToInternet(rvMoreDeal)) {
            Call<DealModel> call = RetrofitClient.getInstance().get_all_deal(utils.getString(Const.ACCESS_TOKEN, ""),
                    utils.getString(Const.LATITUDE, ""),
                    utils.getString(Const.LONGITUDE, ""),
                    "",
                    String.valueOf(mDealModel.getOutlet_id()),
                    "",
                    "",
                    "",
                    String.valueOf(offSet),
                    "",
                    "",
                    Const.TYPE_ELSE);

            call.enqueue(new Callback<DealModel>() {
                @Override
                public void onResponse(Call<DealModel> call, Response<DealModel> response) {
                    if (response.body().getStatusCode() == Const.RESPONSE_SUCCESS) {
                        if (response.body().getDeals().size() > 0) {
                            offSet++;
                        }
                        mDealList.addAll(response.body().getDeals());
                        setMoreDealsAdapter();

                    } else if (response.body().getStatusCode() == Const.BAD_REQUEST) {
                        showAlertSnackBar(rvMoreDeal, response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<DealModel> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
