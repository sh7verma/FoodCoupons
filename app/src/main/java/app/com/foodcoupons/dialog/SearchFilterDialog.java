package app.com.foodcoupons.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.FilterDataAdapter;
import app.com.foodcoupons.adapters.FilterPriceAdapter;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.FilterModel;
import app.com.foodcoupons.models.FilterSingletonModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 24/4/18.
 */

public class SearchFilterDialog implements View.OnClickListener {

    static private Dialog mDialog;
    @BindView(R.id.txt_store)
    TextView txtStore;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.txt_cuisine)
    TextView txtCuisine;
    @BindView(R.id.txt_cancel)
    TextView txtCancel;
    @BindView(R.id.txt_apply)
    TextView txtApply;
    @BindView(R.id.ed_search_store)
    EditText edSearchStore;
    @BindView(R.id.rv_store)
    RecyclerView rvStore;
    @BindView(R.id.rv_price)
    RecyclerView rvPrice;
    @BindView(R.id.rv_cuisine)
    RecyclerView rvCuisine;
    @BindView(R.id.txt_clear)
    TextView txtClear;

    List<FilterModel.BrandModel> brandsList = new ArrayList<>();
    List<FilterModel.BrandModel> cuisineList = new ArrayList<>();
    List<FilterModel.PriceModel> priceList = new ArrayList<>();

    private Typeface typefaceRegular;
    private FilterDataAdapter mAdapterStore, mAdapterCuisine;
    private FilterPriceAdapter mAdapterPrice;
    private FilterSingletonModel mFilterModel;
    private InterfacesCall.FilterApplied mFilterApplied;
    private Context mContext;

    public SearchFilterDialog(Context context, InterfacesCall.FilterApplied filterApplied) {
        mContext = context;
        mFilterApplied = filterApplied;
        showDialog();
    }

    public void showDialog() {
        View view;
        if (mDialog == null) {
            mFilterModel = FilterSingletonModel.getInstance();
            mDialog = new Dialog(mContext);
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            view = View.inflate(mContext, R.layout.dialog_search_filter, null);
            mDialog.setContentView(view);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSideAnimation;
//            mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mDialog.show();

            ButterKnife.bind(this, view);
            initUI();
            initListeners();
            mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mDialog = null;
                }
            });
        }
    }


    private void initUI() {
        typefaceRegular = Typeface.createFromAsset(mContext.getAssets(), "fonts/Brandon_reg.otf");
        edSearchStore.setTypeface(typefaceRegular);

        setData();

        rvStore.setLayoutManager(new LinearLayoutManager(mContext));
        rvPrice.setLayoutManager(new LinearLayoutManager(mContext));
        rvCuisine.setLayoutManager(new LinearLayoutManager(mContext));

        setHeaderSelection(1);
    }

    void setCuisineAdapter() {
        mAdapterCuisine = new FilterDataAdapter(mContext, cuisineList);
        rvCuisine.setAdapter(mAdapterCuisine);
    }

    void setPriceAdapter() {
        mAdapterPrice = new FilterPriceAdapter(mContext, priceList);
        rvPrice.setAdapter(mAdapterPrice);
    }

    void setStoreAdapter() {
        mAdapterStore = new FilterDataAdapter(mContext, brandsList);
        rvStore.setAdapter(mAdapterStore);
    }


    private void initListeners() {
        txtStore.setOnClickListener(this);
        txtPrice.setOnClickListener(this);
        txtCuisine.setOnClickListener(this);
        txtCancel.setOnClickListener(this);
        txtApply.setOnClickListener(this);
        txtClear.setOnClickListener(this);

        edSearchStore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_store:
                setHeaderSelection(1);
                break;
            case R.id.txt_price:
                setHeaderSelection(2);
                break;
            case R.id.txt_cuisine:
                setHeaderSelection(3);
                break;
            case R.id.txt_cancel:
                dismiss();
                break;
            case R.id.txt_apply:
                applyFilter();
                mFilterApplied.onFilterApplied();
                dismiss();
                break;
            case R.id.txt_clear:
                mFilterModel.resetData();
                setData();
                break;
        }
    }

    private void applyFilter() {

        for (int i = 0; i < cuisineList.size(); i++) {
            mFilterModel.getCuisine().get(i).setId(cuisineList.get(i).getId());
            mFilterModel.getCuisine().get(i).setName(cuisineList.get(i).getName());
            mFilterModel.getCuisine().get(i).setSelected(cuisineList.get(i).isSelected());
        }

        for (int i = 0; i < priceList.size(); i++) {
            mFilterModel.getPrice().get(i).setId(priceList.get(i).getId());
            mFilterModel.getPrice().get(i).setSelected(priceList.get(i).isSelected());
            mFilterModel.getPrice().get(i).setMax(priceList.get(i).getMax());
            mFilterModel.getPrice().get(i).setMin(priceList.get(i).getMin());
        }

        for (int i = 0; i < brandsList.size(); i++) {
            mFilterModel.getBrands().get(i).setId(brandsList.get(i).getId());
            mFilterModel.getBrands().get(i).setName(brandsList.get(i).getName());
            mFilterModel.getBrands().get(i).setSelected(brandsList.get(i).isSelected());
        }
    }

    public void setHeaderSelection(int selected) {

        txtPrice.setBackgroundResource(R.color.grey_line);
        rvPrice.setVisibility(View.GONE);
        txtPrice.setTextColor(mContext.getResources().getColor(R.color.black));

        txtCuisine.setBackgroundResource(R.color.grey_line);
        rvCuisine.setVisibility(View.GONE);
        txtCuisine.setTextColor(mContext.getResources().getColor(R.color.black));

        txtStore.setBackgroundResource(R.color.grey_line);
        edSearchStore.setVisibility(View.GONE);
        rvStore.setVisibility(View.GONE);
        txtStore.setTextColor(mContext.getResources().getColor(R.color.black));

        if (selected == 1) {
            txtStore.setBackgroundResource(R.color.colorPrimary);
            txtStore.setTextColor(mContext.getResources().getColor(R.color.white));
            edSearchStore.setVisibility(View.VISIBLE);
            rvStore.setVisibility(View.VISIBLE);
        } else if (selected == 2) {
            txtPrice.setBackgroundResource(R.color.colorPrimary);
            rvPrice.setVisibility(View.VISIBLE);
            edSearchStore.setVisibility(View.GONE);
            txtPrice.setTextColor(mContext.getResources().getColor(R.color.white));

        } else if (selected == 3) {
            txtCuisine.setBackgroundResource(R.color.colorPrimary);
            rvCuisine.setVisibility(View.VISIBLE);
            edSearchStore.setVisibility(View.GONE);
            txtCuisine.setTextColor(mContext.getResources().getColor(R.color.white));
        }

    }

    private void setData() {
        edSearchStore.setText("");

        if (mFilterModel.getPrice().size() == 0) {
            ArrayList<FilterSingletonModel.PriceModel> mPriceList = new ArrayList<>();
            mPriceList.add(new FilterSingletonModel.PriceModel("2", "10", 1, false));
            mPriceList.add(new FilterSingletonModel.PriceModel("10", "20", 1, false));
            mPriceList.add(new FilterSingletonModel.PriceModel("20", "50", 1, false));
            mPriceList.add(new FilterSingletonModel.PriceModel("50", "Above", 1, false));
            mFilterModel.setPrice(mPriceList);
        }

        cuisineList = new ArrayList<>();
        brandsList = new ArrayList<>();
        priceList = new ArrayList<>();


        for (int i = 0; i < mFilterModel.getCuisine().size(); i++) {
            FilterModel.BrandModel model = new FilterModel.BrandModel(mFilterModel.getCuisine().get(i).getName(),
                    mFilterModel.getCuisine().get(i).getId(),
                    mFilterModel.getCuisine().get(i).isSelected());
            cuisineList.add(model);
        }

        for (int i = 0; i < mFilterModel.getPrice().size(); i++) {
            FilterModel.PriceModel model = new FilterModel.PriceModel(mFilterModel.getPrice().get(i).getMin(),
                    mFilterModel.getPrice().get(i).getMax(),
                    mFilterModel.getPrice().get(i).getId(),
                    mFilterModel.getPrice().get(i).isSelected());
            priceList.add(model);
        }

        for (int i = 0; i < mFilterModel.getBrands().size(); i++) {
            FilterModel.BrandModel model = new FilterModel.BrandModel(mFilterModel.getBrands().get(i).getName(),
                    mFilterModel.getBrands().get(i).getId(),
                    mFilterModel.getBrands().get(i).isSelected());
            brandsList.add(model);
        }

        setCuisineAdapter();
        setPriceAdapter();
        setStoreAdapter();
    }

    private void filterList(String str) {
        List<FilterModel.BrandModel> mList = new ArrayList<>();
        if (str.trim().length() > 0) {
            for (FilterModel.BrandModel filterDataModel : brandsList) {
                if (filterDataModel.getName().toLowerCase().contains(str.toLowerCase())) {
                    mList.add(filterDataModel);
                }
            }
            mAdapterStore = new FilterDataAdapter(mContext, mList);
            rvStore.setAdapter(mAdapterStore);
        } else {
            setStoreAdapter();
        }
    }

    void dismiss() {
        mDialog.dismiss();
        mDialog = null;
    }
}
