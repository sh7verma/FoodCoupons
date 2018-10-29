package app.com.foodcoupons.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.adapters.IntroAdapter;
import app.com.foodcoupons.customviews.CirclePageIndicator;
import app.com.foodcoupons.models.IntroModel;
import app.com.foodcoupons.utils.Const;
import butterknife.BindView;

public class IntroActivity extends BaseActivity {

    @BindView(R.id.vp_walk)
    ViewPager vpWalk;
    @BindView(R.id.cp_indicator)
    CirclePageIndicator cpIndicator;
    @BindView(R.id.txt_next)
    TextView txtNext;
    @BindView(R.id.ll_next)
    LinearLayout llNext;

    ArrayList<IntroModel> mWalkArray;

    @Override
    protected int getContentView() {
        return R.layout.activity_walk_through;
    }

    @Override
    protected void onCreateStuff() {
        initList();
        vpWalk.setAdapter(new IntroAdapter(this, mWalkArray));
        cpIndicator.setViewPager(vpWalk);
    }

    private void initList() {
        mWalkArray = new ArrayList<>();
        mWalkArray.add(new IntroModel(getString(R.string.walk_title1), getString(R.string.Lorem_Ipsum_larger), R.mipmap.img_walk1));
        mWalkArray.add(new IntroModel(getString(R.string.walk_title2), getString(R.string.Lorem_Ipsum_larger), R.mipmap.img_walk2));
        mWalkArray.add(new IntroModel(getString(R.string.walk_title3), getString(R.string.Lorem_Ipsum_larger), R.mipmap.img_walk3));
        mWalkArray.add(new IntroModel(getString(R.string.walk_title4), getString(R.string.Lorem_Ipsum_larger), R.mipmap.img_walk4));
    }

    @Override
    protected void initUI() {
        llNext.setOnClickListener(this);

    }

    @Override
    protected void initListener() {
        vpWalk.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == mWalkArray.size() - 1) {
                    txtNext.setText(getString(R.string.let_go));
                    txtNext.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                } else {
                    txtNext.setText(getString(R.string.next));
                    txtNext.setTextColor(mContext.getResources().getColor(R.color.grey_text));
                }
            }

            @Override
            public void onPageSelected(int position) {
                cpIndicator.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
            case R.id.ll_next:
                if (vpWalk.getCurrentItem() == (mWalkArray.size() - 1)) {
                    utils.setInt(Const.INTRO, 1);
                    Intent intent = new Intent(mContext, EnterNumberActivity.class);
                    finish();
                    startActivity(intent);
                } else {
                    vpWalk.setCurrentItem(vpWalk.getCurrentItem() + 1);
                }
                break;
        }
    }


}
