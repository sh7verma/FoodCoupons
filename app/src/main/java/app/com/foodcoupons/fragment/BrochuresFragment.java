package app.com.foodcoupons.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.BrounchersAdapter;
import app.com.foodcoupons.models.BroucherModel;
import butterknife.BindView;

/**
 * Created by dev on 28/9/18.
 */

public class BrochuresFragment extends BaseFragment {

    @SuppressLint("StaticFieldLeak")
    static BrochuresFragment fragment;
    @SuppressLint("StaticFieldLeak")
    static Context mContext;

    @BindView(R.id.recycler_brochures_activity)
    RecyclerView recyclerViewBroucher;
    ArrayList<BroucherModel> arrayList = new ArrayList<>();
    String[] mPlaceList = {"KFC", "Subway", "Dominos", "Uncle Jacks","Star Bucks"};
    String[] mPlaceAddress = {"32 Sector,Chandigarh,Punjab", "JLPL 82 Sector,Mohali,Punjab", "3B2,Mohali,Punjab", "3B2,Mohali,Punjab","Connaught Circus, Connaught Place, New Delhi"};
    String[] mDealsList = {"15", "8", "10", "12","3"};
    Integer[] mBgList = {R.drawable.kfc, R.drawable.subway, R.drawable.dominos, R.drawable.uncle_jacks,R.drawable.starbucks};
    Integer[] mRankingList = {5, 4, 3, 4,3};
    BroucherModel model;
    BrounchersAdapter brounchersAdapter;

    public static BrochuresFragment newInstance(Context context) {
        fragment = new BrochuresFragment();
        mContext = context;
        return fragment;
    }


    @Override

    protected int getContentView() {
        return R.layout.fragment_brochures;
    }

    @Override
    protected void onCreateStuff() {
        recyclerViewBroucher.setLayoutManager(new LinearLayoutManager(mContext));
        for (int i = 0; i < mPlaceList.length; i++) {
            model = new BroucherModel(mPlaceList[i], mPlaceAddress[i], mDealsList[i], mRankingList[i], mBgList[i]);
            arrayList.add(model);
        }
        recyclerViewBroucher.setLayoutManager(new LinearLayoutManager(mContext));
        brounchersAdapter = new BrounchersAdapter(mContext, arrayList);
        recyclerViewBroucher.setAdapter(brounchersAdapter);
    }

    @Override
    protected void initListeners() {

    }
}
