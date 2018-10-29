package app.com.foodcoupons.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;

import app.com.foodcoupons.R;
import app.com.foodcoupons.models.IntroModel;

public class IntroAdapter extends PagerAdapter {

    private final Context context;
    private final ArrayList<IntroModel> images;
    LayoutInflater mLayoutInflater;

    public IntroAdapter(Context context, ArrayList<IntroModel> images){

        this.context = context;
        this.images = images;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_intro, container, false);

        ImageView imageView = itemView.findViewById(R.id.iv_screens);
        TextView txtDecription = itemView.findViewById(R.id.txt_description);
        TextView txtTitle = itemView.findViewById(R.id.txt_title);

        txtTitle.setText(images.get(position).getTitle());
        txtDecription.setText(images.get(position).getDescription());

        imageView.setImageResource(images.get(position).getImage());
        container.addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
