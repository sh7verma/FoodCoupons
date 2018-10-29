package app.com.foodcoupons.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.com.foodcoupons.R;
import app.com.foodcoupons.helper.InterfacesCall;
import app.com.foodcoupons.models.FilterSingletonModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dev on 10/10/18.
 */

public class SortPriceDialog implements View.OnClickListener {

    static private Dialog mDialog;

    @BindView(R.id.txt_price_high_low)
    TextView txtPriceHighLow;
    @BindView(R.id.txt_price_low_high)
    TextView txtPriceLowHigh;

    private InterfacesCall.FilterApplied mFilterApplied;
    private FilterSingletonModel mFilterModel;
    private Context mContext;

    public SortPriceDialog(Context context, InterfacesCall.FilterApplied filterApplied) {
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
            view = View.inflate(mContext, R.layout.dialog_sort_price, null);
            mDialog.setContentView(view);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            mDialog.getWindow().setGravity(Gravity.BOTTOM);
            mDialog.getWindow().getAttributes().windowAnimations = R.style.DialogSideAnimation;
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
        setSelected();
    }

    private void setSelected() {
        if (mFilterModel.getSortPrice().equals("1")) {
            txtPriceHighLow.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            txtPriceLowHigh.setTextColor(mContext.getResources().getColor(R.color.grey_text));
        } else {
            txtPriceHighLow.setTextColor(mContext.getResources().getColor(R.color.grey_text));
            txtPriceLowHigh.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    private void initListeners() {
        txtPriceHighLow.setOnClickListener(this);
        txtPriceLowHigh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_price_high_low:
                mFilterModel.setSortPrice("1");
                mFilterApplied.onFilterApplied();
                dismiss();
                break;

            case R.id.txt_price_low_high:
                mFilterModel.setSortPrice("0");
                mFilterApplied.onFilterApplied();
                dismiss();
                break;
        }

    }

    void dismiss() {
        mDialog.dismiss();
        mDialog = null;
    }
}

