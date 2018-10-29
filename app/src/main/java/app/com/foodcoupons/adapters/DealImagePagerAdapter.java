package app.com.foodcoupons.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.DealModel;

/**
 * Created by dev on 9/10/18.
 */

public class DealImagePagerAdapter extends PagerAdapter {


    ImageView imageView;
    ArrayList<DealModel.DealsBean.ImagesBean> mData = new ArrayList<>();
    Context mContext;

    public DealImagePagerAdapter(Context context, ArrayList<DealModel.DealsBean.ImagesBean> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_viewpager_getdeal, container, false);

        imageView = view.findViewById(R.id.img_deal_item);

        Picasso.get()
                .load(mData.get(position).getImage())
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

}
